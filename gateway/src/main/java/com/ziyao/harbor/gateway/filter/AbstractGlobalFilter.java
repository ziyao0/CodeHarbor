package com.ziyao.harbor.gateway.filter;

import com.ziyao.harbor.core.lang.NonNull;
import com.ziyao.harbor.gateway.core.FailureHandler;
import com.ziyao.harbor.gateway.core.GatewayStopWatches;
import com.ziyao.harbor.gateway.core.support.RequestAttributes;
import com.ziyao.harbor.gateway.support.ApplicationContextUtils;
import lombok.Getter;
import org.springframework.beans.factory.BeanNameAware;
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
@Getter
public abstract class AbstractGlobalFilter implements GlobalFilter, BeanNameAware, Ordered {

    private String beanName;

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
        GatewayStopWatches.start(this.beanName, exchange);
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
     * @param exchange HTTP 请求-响应交互的协定。提供对 HTTP 请求和响应的访问，并公开其他与服务器端处理相关的属性和功能，例如请求属性。
     * @return a {@link Mono} falling back upon source onError
     * @see reactor.core.publisher.Flux#onErrorResume(Function)
     */
    protected Mono<Void> onError(ServerWebExchange exchange, Throwable throwable) {
        FailureHandler failureHandler = ApplicationContextUtils.getBean(FailureHandler.class);
        Mono<Void> resume = failureHandler.onFailureResume(exchange, throwable);
        GatewayStopWatches.stop(this.beanName, exchange);
        return resume;
    }

    /**
     * 判断当前请求是否已经鉴权通过
     *
     * @param exchange HTTP 请求-响应交互的协定。提供对 HTTP 请求和响应的访问，并公开其他与服务器端处理相关的属性和功能，例如请求属性。
     * @return <code>true</code> 表示已经通过鉴权
     */
    protected boolean isAuthenticated(ServerWebExchange exchange) {
        return RequestAttributes.isAuthenticated(exchange);
    }

    @Override
    public void setBeanName(@NonNull String beanName) {
        this.beanName = beanName;
    }

}
