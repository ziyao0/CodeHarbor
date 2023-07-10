package com.ziyao.cfx.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.ziyao.cfx.common.jwt.Tokens;
import com.ziyao.cfx.common.writer.Errors;
import com.ziyao.cfx.gateway.common.config.GatewayConfig;
import com.ziyao.cfx.gateway.support.IPUtils;
import com.ziyao.cfx.gateway.support.SecurityPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;
import reactor.core.publisher.MonoSink;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@Component
@Order(Integer.MIN_VALUE)
public class GatewayPreFilter implements GlobalFilter {

    @Autowired
    private GatewayConfig gatewayConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //处理请求api
        return Mono.create((Consumer<MonoSink<String>>) monoSink -> {
                    String api = exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_PREDICATE_PATH_CONTAINER_ATTR).toString();
                    // 处理黑名单
                    boolean illegal = SecurityPredicate.initIllegalApis(gatewayConfig.getDefaultDisallowApis())
                            .addIllegalApis(gatewayConfig.getDisallowApis()).isIllegal(api);
                    if (illegal)
                        monoSink.error(new IllegalAccessException());
                    else
                        monoSink.success(api);
                })
                .map(api -> {
                    // 处理白名单
                    return SecurityPredicate.initSecurityApis(gatewayConfig.getSkipApis())
                            .addSecurityApis(gatewayConfig.getDefaultSkipApis()).skip(api);
                })
                .flatMap(res -> {
                    if (res) {
                        exchange.getAttributes().put(Tokens.SECURITY, true);
                    }
                    return chain.filter(exchange);
                })
                // 处理异常
                .onErrorResume((Function<Throwable, Mono<Void>>) throwable -> {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.FORBIDDEN);

                    DataBuffer dataBuffer = exchange.getResponse()
                            .bufferFactory()
                            .wrap(JSON.toJSONString(Errors.FORBIDDEN).getBytes());
                    return response.writeWith(MonoOperator.just(dataBuffer));
                })
                .doFirst(() -> exchange.getRequest().mutate()
                        .header(Tokens.IP, IPUtils.getIP(exchange))
                        .build());
    }
}
