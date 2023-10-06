package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.gateway.core.GatewayStopWatches;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public abstract class AbstractGlobalFilter implements GlobalFilter, Ordered {

    private final String id;

    protected AbstractGlobalFilter(String id) {
        this.id = id;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // @formatter:off
        // @formatter:on
        GatewayStopWatches.start(this.id, exchange);
        return doFilter(exchange, chain)
//                .onErrorResume(this::onError)
                .doFinally(signalType -> GatewayStopWatches.stop(id, exchange));
    }

    protected abstract Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain);

//    protected abstract Mono<Void> onError(Throwable throwable);
}
