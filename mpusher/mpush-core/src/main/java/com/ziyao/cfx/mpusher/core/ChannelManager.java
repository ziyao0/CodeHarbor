package com.ziyao.cfx.mpusher.core;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public abstract class ChannelManager {

    private ChannelManager() {

    }

    private static final ConcurrentMap<String, Channel> CONCURRENT_MAP;
    // 广播通道存储
    private static final ChannelGroup channels;

    static {
        CONCURRENT_MAP = PlatformDependent.newConcurrentHashMap();
        channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    /**
     * 存储用户管道
     *
     * @param channel {@link Channel}
     */
    public static void storage(Channel channel) {
        if (channel == null) {
            throw new ChannelException("管道不存在!");
        }
        CONCURRENT_MAP.put(channel.id().asLongText(), channel);
    }

    public static void setBroadcast(Channel channel) {
        channels.add(channel);
    }

    public static ChannelGroup channels() {
        return channels;
    }

    /**
     * Get the pipeline
     *
     * @param xid {@link Channel#id()} channelID
     * @return return channel
     * @see Channel
     */
    public static Channel get(String xid) {
        if (xid == null) {
            return null;
        }
        return CONCURRENT_MAP.get(xid);
    }

    /**
     * 通过管道id集合获取管道信息
     *
     * @param xIds {@link Channel#id()} channelID
     * @return return channel
     * @see Channel
     */
    public static ChannelGroup get(Collection<String> xIds) {

        if (xIds == null || xIds.isEmpty()) {
            return null;
        }
        ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        for (String xId : xIds) {
            Channel channel = CONCURRENT_MAP.get(xId);
            if (channel != null) {
                channels.add(channel);
            }
        }
        if (channels.isEmpty()) {
            return null;
        }
        return channels;
    }

    /**
     * Remove Not survived pipes
     *
     * @param ctx {@link ChannelHandlerContext#channel()}
     */
    public static void remove(ChannelHandlerContext ctx) {
        String s = ctx.channel().id().asLongText();
        CONCURRENT_MAP.remove(ctx.channel().id().asLongText());
    }
}
