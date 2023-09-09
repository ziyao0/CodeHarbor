package com.ziyao.harbor.gateway.filter;

import com.google.common.base.Function;
import com.ziyao.harbor.core.error.HarborExceptions;
import com.ziyao.harbor.core.token.TokenType;
import com.ziyao.harbor.core.token.Tokens;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.gateway.core.*;
import com.ziyao.harbor.gateway.core.token.AccessControl;
import com.ziyao.harbor.gateway.core.token.AccessToken;
import com.ziyao.harbor.gateway.core.token.Authorization;
import com.ziyao.harbor.gateway.core.token.SuccessAuthorization;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;
import reactor.core.publisher.MonoSink;

import java.util.function.Consumer;

/**
 * @author ziyao zhang
 * @since 2023/5/17
 */
@Slf4j
@Component
@Order(0)
public class AccessFilter implements GlobalFilter {


    @Autowired
    private AuthorizationProcessor authorizationProcessor;
    @Autowired
    private SuccessfulHandler<SuccessAuthorization> successfulHandler;
    @Autowired
    private FailureHandler failureHandler;
    @Resource
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 从请求头提取认证token
        AccessControl accessControl = AccessTokenExtractor.extractForHeaders(exchange);
        // 快速校验认证token
        AccessTokenValidator.validateToken(accessControl);


        Boolean isSecurity = exchange.getAttributeOrDefault("SECURITY", false);
        if (isSecurity) {
            return chain.filter(exchange);
        }
        return MonoOperator.create((Consumer<MonoSink<Authorization>>) monoSink -> {
                    String authToken = exchange.getRequest().getHeaders().getFirst(Tokens.AUTHORIZATION);
                    if (Strings.hasLength(authToken) && authToken.startsWith(TokenType.Bearer.getType()))
                        monoSink.success((Authorization) () -> authToken.substring(7));
                    else
                        monoSink.error(HarborExceptions.createUnauthorizedException(""));
                }).flatMap(authorization -> {
                    Authorization author = authorizationProcessor.process(authorization);
                    if (author.isAuthorized()) {
                        successfulHandler.onSuccessful(exchange, (SuccessAuthorization) author);
                        return chain.filter(exchange);
                    } else {
                        return MonoOperator.error(HarborExceptions.createUnauthorizedException(author.getMessage()));
                    }
                })
                .onErrorResume((Function<Throwable, Mono<Void>>) throwable -> {
                    DataBuffer dataBuffer = failureHandler.onFailureResume(exchange, throwable);
                    ServerHttpResponse response = exchange.getResponse();
                    return response.writeWith(MonoOperator.just(dataBuffer));
                });
    }

}
