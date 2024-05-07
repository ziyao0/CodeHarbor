package com.ziyao.harbor.im.core;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public interface Listener {
    /**
     * Listener The monitoring service started successfully
     *
     * @param args args
     */
    void success(Object... args);

    /**
     * Listener service startup exception
     *
     * @param cause Throwable
     */
    void failure(Throwable cause);
}
