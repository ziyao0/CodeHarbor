package com.ziyao.cfx.mpusher.codec;

import com.ziyao.cfx.mpusher.api.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(MessageEncoder.class);

    private final Class<?> genericClass;

    public MessageEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        if (genericClass.isInstance(message)) {
            byte[] data = Protostuff.serialize(message);
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }
}
