package com.ziyao.cfx.mpusher.client.core;

import com.ziyao.cfx.mpusher.api.Live;
import com.ziyao.cfx.mpusher.api.Message;
import com.ziyao.cfx.mpusher.api.State;
import com.ziyao.cfx.mpusher.codec.Protostuff;
import com.ziyao.cfx.mpusher.core.ChannelManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author ziyao zhang
 * @since 2023/6/30
 */
@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(ClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("Connection SUCCESS!");
        Channel channel = ctx.channel();
        ChannelManager.storage(channel);
        // Send heartbeat
        channel.writeAndFlush(Protostuff.serializer(new Message(State.PING, Live.PING)));
    }
}
