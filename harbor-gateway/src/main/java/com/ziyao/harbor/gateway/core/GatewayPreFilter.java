package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.gateway.config.GatewayConfig;
import com.ziyao.harbor.gateway.filter.AbstractGlobalFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ziyao zhang
 * @since 2023/10/7
 */
@Component
public class GatewayPreFilter extends AbstractGlobalFilter {

    @Autowired
    private GatewayConfig gatewayConfig;

    protected GatewayPreFilter() {
        super(GatewayPreFilter.class.getSimpleName());
    }

    @Override
    protected Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
