package com.ziyao.harbor.im.core;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public abstract class PipelineHolder {

    private PipelineHolder() {
        throw new IllegalStateException("this class is utility!");
    }

    private static ChannelPipeline pipeline;

    public static void createPipeline(SocketChannel channel) {
        if (channel == null) {
            return;
        }
        pipeline = channel.pipeline();
    }

    public static void addLast(ChannelHandler... handlers) {
        pipeline.addLast(handlers);
    }

    public static void addLast(String name, ChannelHandler handlers) {
        pipeline.addLast(name, handlers);
    }

    public static ChannelHandler get(String name) {
        return pipeline.get(name);
    }
}