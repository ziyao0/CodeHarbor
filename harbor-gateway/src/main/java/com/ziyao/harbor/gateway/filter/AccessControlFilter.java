package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.core.error.HarborExceptions;
import com.ziyao.harbor.gateway.core.*;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import com.ziyao.harbor.gateway.core.token.SuccessAuthorization;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private SuccessfulHandler<SuccessAuthorization> successfulHandler;
    @Autowired
    private FailureHandler failureHandler;
    @Resource
    private ProviderManager providerManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 从请求头提取认证token
        AccessControl accessControl = AccessTokenExtractor.extractForHeaders(exchange);
        // 快速校验认证token
        AccessTokenValidator.validateToken(accessControl);


        return providerManager.authorize(accessControl).flatMap(author -> {
                    if (author.isAuthorized()) {
                        successfulHandler.onSuccessful(exchange, (SuccessAuthorization) author);
                        return chain.filter(exchange);
                    } else {
                        return MonoOperator.error(HarborExceptions.createUnauthorizedException(author.getMessage()));
                    }
                })
                .onErrorResume(throwable -> failureHandler.onFailureResume(exchange, throwable));
    }

}
