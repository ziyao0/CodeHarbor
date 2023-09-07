package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.core.error.Errors;
import com.ziyao.harbor.gateway.config.GatewayConfig;
import com.ziyao.harbor.gateway.core.DataBuffers;
import com.ziyao.harbor.gateway.core.SecurityPredicate;
import com.ziyao.harbor.gateway.support.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.function.Consumer;

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
                        exchange.getAttributes().put("SECURITY", true);
                    }
                    return chain.filter(exchange);
                })
                // 处理异常
                .onErrorResume(throwable -> DataBuffers.writeWith(exchange.getResponse(), Errors.FORBIDDEN, HttpStatus.FORBIDDEN))
                .doFirst(() -> exchange.getRequest().mutate()
                        .header("ip", IPUtils.getIP(exchange))
                        .build());
    }
}
