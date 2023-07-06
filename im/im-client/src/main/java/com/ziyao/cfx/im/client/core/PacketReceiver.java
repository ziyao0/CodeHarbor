package com.ziyao.cfx.im.client.core;

import com.ziyao.cfx.im.api.Connection;
import com.ziyao.cfx.im.api.Packet;
import io.netty.channel.Channel;

/**
 * @author ziyao zhang
 * @since 2023/7/3
 */
public interface PacketReceiver {
    void onReceive(Packet packet, Channel channel);
}
