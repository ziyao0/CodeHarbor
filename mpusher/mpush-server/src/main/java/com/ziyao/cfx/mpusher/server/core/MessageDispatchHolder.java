package com.ziyao.cfx.mpusher.server.core;

import com.ziyao.cfx.mpusher.api.Message;
import com.ziyao.cfx.mpusher.core.ChannelManager;
import com.ziyao.cfx.mpusher.core.DispatchHolder;
import io.netty.channel.Channel;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class MessageDispatchHolder implements DispatchHolder {


    public void send(Message message) {
        switch (message.getType()) {
            case BROADCAST -> {
                ChannelManager.channels().writeAndFlush(message);
            }
            case UNICAST -> {
                Channel channel = ChannelManager.get("");
                channel.writeAndFlush(message);
            }
            default -> throw new IllegalStateException("Unexpected value: " + message.getType());
        }
    }


    /**
     * User online resend but not executed{@link com.ziyao.cfx.mpusher.api.State#OPEN}'S news
     *
     * @param userId userId
     */
    public void retry(String userId) {
    }

    /**
     * Message confirmation
     *
     * @param messageId message code
     * @see com.ziyao.cfx.mpusher.api.State#ACK
     */
    public void ack(String messageId) {

    }


}
