package com.cfx.gateway.security.api;

import org.springframework.web.server.ServerWebExchange;

import java.util.function.BiFunction;

/**
 * @author Eason
 * @since 2023/5/16
 */
public interface FailureHandler<T> {

    /**
     * 失败时调用
     * <p>
     * {@link Throwable}              异常信息
     * T 返回类型
     */
    BiFunction<Throwable, ServerWebExchange, T> onFailureResume();

}
