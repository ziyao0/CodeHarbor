package com.ziyao.harbor.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 鉴权成功后执行的过滤器
 *
 * @author ziyao zhang
 * @since 2023/10/7
 */
public abstract class AbstractAfterAuthenticationFilter extends AbstractGlobalFilter {

    @Override
    protected Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isAuthenticated(exchange)) {
            return onSuccess(exchange, chain);
        }
        return chain.filter(exchange);
    }

    /**
     * 授权成功后执行
     *
     * @param exchange the current server exchange
     * @param chain    允许 {@link GatewayFilter} 委托给链中的下一个的协定
     * @return {@code Mono<Void>} 指示请求处理何时完成
     */
    protected abstract Mono<Void> onSuccess(ServerWebExchange exchange, GatewayFilterChain chain);
}
