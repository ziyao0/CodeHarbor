package com.ziyao.harbor.im.core;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public interface Starter {

    /**
     * 用于netty server初始化
     *
     * @see io.netty.bootstrap.ServerBootstrap
     */
    void init();

    /**
     * Used for netty service startup
     *
     * @throws InterruptedException netty Interrupt exception
     */
    void start() throws InterruptedException;

    /**
     * to netty server close
     *
     * @throws InterruptedException Interrupt exception
     * @see io.netty.bootstrap.ServerBootstrap#bind()
     */
    void close() throws InterruptedException;

    /**
     * judgment netty server if running
     *
     * @return judgment result
     */
    default boolean isRunning() {
        return false;
    }


}
