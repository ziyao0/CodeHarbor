package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.core.error.HarborExceptions;
import com.ziyao.harbor.gateway.core.*;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import com.ziyao.harbor.gateway.core.token.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;

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
    private final ProviderManager providerManager;

    public AccessControlFilter(
            SuccessfulHandler<Authorization> successfulHandler,
            FailureHandler failureHandler,
            ProviderManager providerManager) {
        this.successfulHandler = successfulHandler;
        this.failureHandler = failureHandler;
        this.providerManager = providerManager;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 从请求头提取认证token
        AccessControl accessControl = AccessTokenExtractor.extractForHeaders(exchange);
        // 快速校验认证token
        AccessTokenValidator.validateToken(accessControl);

        return providerManager.authorize(accessControl).flatMap(author -> {
            if (author.isAuthorized()) {
                successfulHandler.onSuccessful(exchange, author);
                return chain.filter(exchange);
            } else {
                return MonoOperator.error(HarborExceptions.createUnauthorizedException(author.getMessage()));
            }
        }).onErrorResume(throwable -> failureHandler.onFailureResume(exchange, throwable));
    }

}
