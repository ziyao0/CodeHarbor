package com.ziyao.cfx.mpusher.api;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * @author ziyao zhang
 * @since 2023/7/3
 */
public interface Connection {

    /**
     * 初始化
     */
    void init(Channel channel);

    /**
     * 发送消息
     *
     * @param packet 数据包
     * @return ChannelFuture
     */
    ChannelFuture send(Packet packet);

    /**
     * @param packet   数据包
     * @param listener 监听
     * @return ChannelFuture
     */
    ChannelFuture send(Packet packet, ChannelFutureListener listener);

    /**
     * 获取channel id
     */
    String getId();

    /**
     * 关闭连接
     */
    ChannelFuture close();

    /**
     * 是否连接
     */
    boolean isConnected();


    /**
     * 获取channel
     *
     * @return {@link Channel}
     */
    Channel getChannel();
}
