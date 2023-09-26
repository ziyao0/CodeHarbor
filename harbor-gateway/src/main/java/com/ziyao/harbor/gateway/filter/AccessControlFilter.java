package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.gateway.config.GatewayConfig;
import com.ziyao.harbor.gateway.core.*;
import com.ziyao.harbor.gateway.core.support.SecurityPredicate;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import com.ziyao.harbor.gateway.core.token.Authorization;
import com.ziyao.harbor.gateway.error.GatewayErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;

import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/5/17
 */
@Slf4j
@Component
@Order(0)
public class AccessControlFilter implements GlobalFilter {


    private final SuccessfulHandler<Authorization> successfulHandler;
    private final FailureHandler failureHandler;
    private final AuthorizerManager authorizerManager;
    private final GatewayConfig gatewayConfig;

    public AccessControlFilter(
            SuccessfulHandler<Authorization> successfulHandler,
            FailureHandler failureHandler,
            AuthorizerManager authorizerManager, GatewayConfig gatewayConfig) {
        this.successfulHandler = successfulHandler;
        this.failureHandler = failureHandler;
        this.authorizerManager = authorizerManager;
        this.gatewayConfig = gatewayConfig;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 从请求头提取认证token
        AccessControl accessControl = AccessTokenExtractor.extractForHeaders(exchange);
        return MonoOperator.just(accessControl).flatMap(access -> {
            boolean skip = SecurityPredicate.initSecurityApis(getSecurityApis()).skip(access.getApi());
            if (skip) {
                return chain.filter(exchange);
            } else {
                // 快速校验认证token
                AccessTokenValidator.validateToken(access);
                return authorizerManager.authorize(access).flatMap(author -> {
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
}
