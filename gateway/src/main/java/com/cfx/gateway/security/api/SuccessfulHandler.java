package com.cfx.gateway.security.api;

import java.util.function.Consumer;

/**
 * @author Eason
 * @since 2023/5/16
 */
public interface SuccessfulHandler<T> {


    /**
     * 成功时调用方法
     *
     * @return {@link Consumer}
     */
    Consumer<T> onSuccessful();
}
