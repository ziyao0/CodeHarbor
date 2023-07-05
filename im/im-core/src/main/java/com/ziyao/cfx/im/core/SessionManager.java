package com.ziyao.cfx.im.core;

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
public interface SessionManager {

    ConcurrentMap<String, ChannelGroup> CONCURRENT_MAP = PlatformDependent.newConcurrentHashMap();
    // 广播通道存储
    ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    static ChannelGroup get(String userId) {
        return CONCURRENT_MAP.get(userId);
    }

    static void removeAndClose(Channel channel) {
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

    static void add(String userId, Channel channel) {
        channels.add(channel);
        ChannelGroup channelGroup = CONCURRENT_MAP.get(userId);
        if (Objects.isNull(channelGroup)) {
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        }
        channelGroup.add(channel);
        CONCURRENT_MAP.put(userId, channelGroup);
    }

    static int getConnNum() {
        return channels.size();
    }

    static void init() {

    }

    static void destroy() {
        channels.forEach(Channel::close);
        channels.clear();
        CONCURRENT_MAP.values().forEach(cg -> cg.forEach(Channel::close));
        CONCURRENT_MAP.clear();
    }

}
