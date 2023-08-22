package com.ziyao.harbor.im.client.core;

import com.ziyao.harbor.im.client.NettyClient;

import java.net.UnknownHostException;

/**
 * @author ziyao zhang
 * @since 2023/7/6
 */
public class ExceptionCaughtCallback {

    private final NettyClient nettyClient;

    public ExceptionCaughtCallback(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    private void reconnection() throws UnknownHostException {
        nettyClient.start();
    }


}
