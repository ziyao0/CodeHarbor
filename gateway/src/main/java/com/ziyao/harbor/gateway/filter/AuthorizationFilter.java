package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.gateway.config.GatewayConfig;
import com.ziyao.harbor.gateway.core.*;
import com.ziyao.harbor.gateway.core.support.RequestAttributes;
import com.ziyao.harbor.gateway.core.support.SecurityPredicate;
import com.ziyao.harbor.gateway.core.token.Authorization;
import com.ziyao.harbor.gateway.core.token.DefaultAccessToken;
import com.ziyao.harbor.gateway.error.GatewayErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;

import java.util.Set;

/**
 * grant
 *
 * @author ziyao zhang
 * @since 2023/5/17
 */
@Slf4j
@Component
public class AuthorizationFilter extends AbstractGlobalFilter {


    private final AuthorizerManager authorizerManager;
    private final GatewayConfig gatewayConfig;

    public AuthorizationFilter(
            AuthorizerManager authorizerManager,
            GatewayConfig gatewayConfig) {
        this.authorizerManager = authorizerManager;
        this.gatewayConfig = gatewayConfig;
    }

    @Override
    protected Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 从请求头提取认证token
        DefaultAccessToken defaultAccessToken = AccessTokenExtractor.extractForHeaders(exchange);
        return Mono.just(defaultAccessToken).flatMap(access -> {
            boolean skip = SecurityPredicate.initSecurityApis(getSecurityApis()).skip(access.getApi());
            Mono<Void> filter;
            if (skip) {
                filter = chain.filter(exchange);
            } else {
                // 快速校验认证token
                AccessTokenValidator.validateToken(access);
                filter = authorizerManager.getAuthorizer(access.getName()).authorize(access)
                        .flatMap(author -> {
                            if (author.isAuthorized()) {
                                // TODO: 2023/10/8 成功后向exchange存储认证成功信息
                                RequestAttributes.storeAuthorizerContext(exchange, null);
                                return chain.filter(exchange);
                            } else {
                                return GatewayErrors.createUnauthorizedException(author.getMessage());
                            }
                        });
            }
            GatewayStopWatches.stop(getBeanName(), exchange);
            return filter;
        });
    }

    private Set<String> getSecurityApis() {
        Set<String> skipApis = gatewayConfig.getDefaultSkipApis();
        skipApis.addAll(gatewayConfig.getSkipApis());
        return skipApis;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
