package com.ziyao.harbor.gateway.core;

import org.springframework.web.server.ServerWebExchange;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
@FunctionalInterface
public interface SuccessfulHandler<T> {

    /**
     * 成功时调用方法
     */
    ServerWebExchange onSuccessful(ServerWebExchange exchange, T t);
}
