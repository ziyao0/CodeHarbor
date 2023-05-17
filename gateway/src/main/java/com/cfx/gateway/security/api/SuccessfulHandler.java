package com.cfx.gateway.security.api;

import org.springframework.web.server.ServerWebExchange;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
public interface SuccessfulHandler<T> {


    /**
     * 成功时调用方法
     */
    void onSuccessful(ServerWebExchange exchange, T t);
}
