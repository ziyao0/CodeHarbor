package com.ziyao.harbor.im.core;

import com.alibaba.fastjson.JSON;
import com.ziyao.harbor.im.api.Agreement;
import com.ziyao.harbor.im.api.Packet;
import com.ziyao.harbor.im.codec.PacketEncoder;
import com.ziyao.harbor.im.codec.Protostuff;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public interface MetricsProcessor {

    InternalLogger LOGGER = InternalLoggerFactory.getInstance(PacketEncoder.class);


    /**
     * Health Monitoring Processor
     *
     * @param ctx       Context object, containing channels{@link io.netty.channel.Channel}
     *                  channel{@link io.netty.channel.ChannelPipeline}
     * @param agreement agreement type
     */
    void process(ChannelHandlerContext ctx, Agreement agreement);

    /**
     * Health Monitoring Processor
     *
     * @param ctx     Context object, containing channels{@link io.netty.channel.Channel}
     *                channel{@link io.netty.channel.ChannelPipeline}
     * @param byteBuf Data sent by the client
     */
    default void process(ChannelHandlerContext ctx, ByteBuf byteBuf) {
    }

    /**
     * 通过请求协议对数据进行转换
     *
     * @param metadata 消息元数据
     * @return Return message object
     * @see Packet  转换
     */
    default Packet transform(Object metadata) {
        switch (Agreement.getInstance(metadata)) {
            case WS -> {
                return JSON.parseObject(((TextWebSocketFrame) metadata).text(), Packet.class);
            }
            case TCP -> {
                return Protostuff.deserializer(Protostuff.byteBufToBytes((ByteBuf) metadata), Packet.class);
            }
            case HTTP -> {
                LOGGER.debug("HTTP protocol requests will not be processed temporarily!");
                return null;
            }
            case UNKNOWN -> {
                LOGGER.error("Unrecognized protocol request!");
                return null;
            }
            default -> {
                return null;
            }
        }
    }
}
