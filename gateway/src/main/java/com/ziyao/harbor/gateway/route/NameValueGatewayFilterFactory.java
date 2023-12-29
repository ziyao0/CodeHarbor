package com.ziyao.harbor.gateway.route;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.cloud.gateway.support.GatewayToStringStyler;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * ConditionalOnEnabledFilter
 *
 * @author ziyao zhang
 * @since 2023/9/7
 */
public class NameValueGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                String value = ServerWebExchangeUtils.expand(exchange, config.getValue());
                ServerHttpRequest request = exchange.getRequest().mutate().headers((httpHeaders) -> {
                    httpHeaders.add(config.getName(), value);
                }).build();
                return chain.filter(exchange.mutate().request(request).build());
            }

            @Override
            public String toString() {
                return GatewayToStringStyler.filterToStringCreator(NameValueGatewayFilterFactory.this).append(config.getName(), config.getValue()).toString();
            }
        };

    }
}
