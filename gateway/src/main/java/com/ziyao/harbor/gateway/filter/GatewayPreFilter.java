package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.gateway.config.LoggerConfig;
import com.ziyao.harbor.gateway.core.GatewayStopWatches;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ziyao zhang
 * @since 2023/10/7
 */
@Component
public class GatewayPreFilter extends AbstractGlobalFilter {

    private final LoggerConfig loggerConfig;

    protected GatewayPreFilter(LoggerConfig loggerConfig) {
        this.loggerConfig = loggerConfig;
    }

    @Override
    protected Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (loggerConfig.isFilterWatch()) {
            GatewayStopWatches.enabled(exchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
