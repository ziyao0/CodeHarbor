package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.gateway.config.GatewayConfig;
import com.ziyao.harbor.gateway.core.*;
import com.ziyao.harbor.gateway.core.support.SecurityPredicate;
import com.ziyao.harbor.gateway.core.token.Authorization;
import com.ziyao.harbor.gateway.core.token.DefaultAccessToken;
import com.ziyao.harbor.gateway.error.GatewayErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;
import reactor.core.scheduler.Schedulers;

import java.util.Set;

/**
 * grant
 *
 * @author ziyao zhang
 * @since 2023/5/17
 */
@Slf4j
@Component
@Order(0)
public class AuthorizationFilter extends AbstractGlobalFilter {


    private final SuccessfulHandler<Authorization> successfulHandler;
    private final FailureHandler failureHandler;
    private final AuthorizerManager authorizerManager;
    private final GatewayConfig gatewayConfig;

    public AuthorizationFilter(
            SuccessfulHandler<Authorization> successfulHandler,
            FailureHandler failureHandler,
            AuthorizerManager authorizerManager, GatewayConfig gatewayConfig) {
        super(AuthorizationFilter.class.getSimpleName());
        this.successfulHandler = successfulHandler;
        this.failureHandler = failureHandler;
        this.authorizerManager = authorizerManager;
        this.gatewayConfig = gatewayConfig;
    }

    @Override
    protected Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 从请求头提取认证token
        DefaultAccessToken defaultAccessToken = AccessTokenExtractor.extractForHeaders(exchange);
        return MonoOperator.just(defaultAccessToken).publishOn(Schedulers.boundedElastic()).flatMap(access -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean skip = SecurityPredicate.initSecurityApis(getSecurityApis()).skip(access.getApi());
            if (skip) {
                return chain.filter(exchange);
            } else {
                // 快速校验认证token
                AccessTokenValidator.validateToken(access);
                return authorizerManager.getAuthorizer(access.getName()).authorize(access)
                        .flatMap(author -> {
                            if (author.isAuthorized()) {
                                return chain.filter(successfulHandler.onSuccessful(exchange, author));
                            } else {
                                return GatewayErrors.createUnauthorizedException(author.getMessage());
                            }
                        }).onErrorResume(t -> failureHandler.onFailureResume(exchange, t));
            }
        }).onErrorResume(t -> failureHandler.onFailureResume(exchange, t));
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
