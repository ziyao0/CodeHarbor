package com.ziyao.cfx.im.core;

import java.net.SocketAddress;

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
    void start(int port) throws InterruptedException;

    /**
     * Used for netty client startup
     *
     * @param socketAddress socketAddress
     * @throws InterruptedException The program terminal is abnormal
     * @see io.netty.bootstrap.ServerBootstrap#bind()
     */
    void start(SocketAddress socketAddress) throws InterruptedException;

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
