package com.ziyao.harbor.im.server.core;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ziyao zhang
 * @since 2023/7/3
 */
public abstract class NettySessionManager {
    private static final ConcurrentMap<String, ChannelGroup> CONCURRENT_MAP = PlatformDependent.newConcurrentHashMap();
    // 广播通道存储
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public static ChannelGroup get(String userId) {
        return CONCURRENT_MAP.get(userId);
    }

    public static void removeAndClose(Channel channel) {
        channels.remove(channel);
        CONCURRENT_MAP.values().forEach(cg -> {
            cg.remove(channel);
        });
        channel.close();
        CONCURRENT_MAP.forEach((key, channels) -> {
            if (channels.size() == 0) {
                CONCURRENT_MAP.remove(key);
            }
        });
    }

    public static void add(String userId, Channel channel) {
        channels.add(channel);
        ChannelGroup channelGroup = CONCURRENT_MAP.get(userId);
        if (Objects.isNull(channelGroup)) {
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        }
        channelGroup.add(channel);
        CONCURRENT_MAP.put(userId, channelGroup);
    }

    public static int getConnNum() {
        return channels.size();
    }

    public static void init() {

    }

    public static ChannelGroup getAll() {
        return channels;
    }

    public static void destroy() {
        channels.forEach(Channel::close);
        channels.clear();
        CONCURRENT_MAP.values().forEach(cg -> cg.forEach(Channel::close));
        CONCURRENT_MAP.clear();
    }

}
