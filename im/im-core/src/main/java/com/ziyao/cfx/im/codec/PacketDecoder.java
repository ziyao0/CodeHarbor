package com.ziyao.cfx.im.codec;

import com.ziyao.cfx.im.api.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class PacketDecoder extends ByteToMessageDecoder {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(ByteToMessageDecoder.class);


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //Need to get binary bytecode -> MyMessageProtocol packet (object)
        if (in.readableBytes() <= 0) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        Object deserializer = Protostuff.deserializer(data, Packet.class);
        out.add(deserializer);
    }
}