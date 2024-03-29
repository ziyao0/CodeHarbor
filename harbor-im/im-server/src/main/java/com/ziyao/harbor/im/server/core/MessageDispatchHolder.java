package com.ziyao.harbor.im.server.core;

import com.ziyao.harbor.im.api.Event;
import com.ziyao.harbor.im.api.Packet;
import com.ziyao.harbor.im.core.DispatchHolder;
import io.netty.channel.group.ChannelGroup;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class MessageDispatchHolder implements DispatchHolder {


    public void send(Packet packet) {
        switch (packet.getType()) {
            case BROADCAST -> {
                NettySessionManager.getAll().writeAndFlush(packet);
            }
            case UNICAST -> {
                ChannelGroup channels = NettySessionManager.get("");
                channels.writeAndFlush(packet);
            }
            default -> throw new IllegalStateException("Unexpected value: " + packet.getType());
        }
    }


    /**
     * User online resend but not executed{@link Event#OPEN}'S news
     *
     * @param userId userId
     */
    public void retry(String userId) {
    }

    /**
     * Message confirmation
     *
     * @param messageId message code
     * @see Event#ACK
     */
    public void ack(String messageId) {

    }


}
