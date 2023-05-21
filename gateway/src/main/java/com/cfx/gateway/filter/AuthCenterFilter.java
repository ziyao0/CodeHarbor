package com.cfx.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.cfx.common.api.IMessage;
import com.cfx.common.exception.UnauthorizedException;
import com.cfx.common.support.Tokens;
import com.cfx.common.writer.Errors;
import com.cfx.gateway.common.config.GatewayConfig;
import com.cfx.gateway.security.api.Authorization;
import com.cfx.gateway.security.api.AuthorizationProcessor;
import com.cfx.gateway.security.api.FailureHandler;
import com.cfx.gateway.security.api.SuccessfulHandler;
import com.cfx.gateway.security.core.SuccessAuthorization;
import com.google.common.base.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
public class AuthCenterFilter implements GlobalFilter {

    @Autowired
    private GatewayConfig gatewayConfig;
    @Autowired
    private AuthorizationProcessor authorizationProcessor;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Boolean isSecurity = exchange.getAttributeOrDefault(Tokens.SECURITY, false);
        if (isSecurity) {
            return chain.filter(exchange);
        }
        return MonoOperator.create((Consumer<MonoSink<Authorization>>) monoSink -> {
                    String authToken = exchange.getRequest().getHeaders().getFirst(Tokens.AUTHORIZATION);
                    if (StringUtils.hasLength(authToken) && authToken.startsWith(Tokens.BEARER))
                        monoSink.success((Authorization) () -> authToken.substring(7));
                    else
                        monoSink.error(new UnauthorizedException());
                }).flatMap(authorization -> {
                    Authorization author = authorizationProcessor.process(authorization);
                    if (author.isAuthorized()) {
                        successfulHandler.onSuccessful(exchange, (SuccessAuthorization) author);
                        return chain.filter(exchange);
                    } else {
                        return MonoOperator.error(new UnauthorizedException(author.getMessage()));
                    }
                })
                .onErrorResume((Function<Throwable, Mono<Void>>) throwable -> {
                    DataBuffer dataBuffer = failureHandler.onFailureResume(exchange, throwable);
                    ServerHttpResponse response = exchange.getResponse();
                    return response.writeWith(MonoOperator.just(dataBuffer));
                });
    }

    /**
     * 鉴权成功处理
     */
    private final SuccessfulHandler<SuccessAuthorization> successfulHandler =
            (exchange, information) -> {
                exchange.getRequest().mutate()
                        .header(Tokens.APP_ID, information.getAppid().toString())
                        .header(Tokens.USER_ID, information.getUserId().toString())
                        .header(Tokens.USERNAME, information.getUsername())
                        .build();
                String refreshToken = Tokens.refresh(information.getToken(), gatewayConfig.getOauth2Security(), false);
                exchange.getResponse().getHeaders().add(Tokens.AUTHORIZATION, Tokens.getBearerToken(refreshToken));
            };


    /**
     * 鉴权失败异常处理
     */
    private final FailureHandler failureHandler =
            (exchange, throwable) -> {
                IMessage iMessage = Errors.INTERNAL_SERVER_ERROR;
                if (throwable instanceof UnauthorizedException) {
                    iMessage = IMessage.getInstance(Errors.E_401, throwable.getMessage());
                } else {
                    log.error("认证异常：{}", throwable.getMessage(), throwable);
                }

                exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(iMessage.getStatus()));
                return exchange.getResponse()
                        .bufferFactory()
                        .wrap(JSON.toJSONString(iMessage).getBytes());
            };
}
