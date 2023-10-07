package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.gateway.core.FailureHandler;
import com.ziyao.harbor.gateway.core.GatewayStopWatches;
import com.ziyao.harbor.gateway.core.support.RequestAttributes;
import com.ziyao.harbor.gateway.support.ApplicationContextUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * 抽象全局过滤器
 *
 * @author ziyao
 * @since 2023/4/23
 */
public abstract class AbstractGlobalFilter implements GlobalFilter, Ordered {

    private final String id;

    protected AbstractGlobalFilter(String id) {
        this.id = id;
    }

    /**
     * 处理 Web 请求并（可选）通过给定的 {@code GatewayFilterChain} 委托给下一个 {@link org.springframework.cloud.gateway.filter.GatewayFilter}。
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} 指示请求处理何时完成
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // @formatter:off
        GatewayStopWatches.start(this.id, exchange);
        return doFilter(exchange, chain)
                .onErrorResume(throwable -> onError(exchange, throwable));
        // @formatter:on
    }

    /**
     * 处理 Web 请求并（可选）通过给定的 {@code GatewayFilterChain} 委托给下一个 {@link org.springframework.cloud.gateway.filter.GatewayFilter}。
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} 指示请求处理何时完成
     */
    protected abstract Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain);

    /**
     * 发生任何错误时订阅回退发布者，使用函数根据错误选择回退。
     *
     * @param exchange the current server exchange
     * @return a {@link Mono} falling back upon source onError
     * @see reactor.core.publisher.Flux#onErrorResume(Function)
     */
    protected Mono<Void> onError(ServerWebExchange exchange, Throwable throwable) {
        FailureHandler failureHandler = ApplicationContextUtils.getBean(FailureHandler.class);
        Mono<Void> resume = failureHandler.onFailureResume(exchange, throwable);
        GatewayStopWatches.stop(id, exchange);
        return resume;
    }

    /**
     * 判断当前请求是否已经鉴权通过
     *
     * @param exchange the current server exchange
     * @return <code>true</code> 表示已经通过鉴权
     */
    protected boolean isAuthenticated(ServerWebExchange exchange) {
        return RequestAttributes.isAuthenticated(exchange);
    }
}
