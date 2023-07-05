package com.ziyao.cfx.im.client.core;

import com.alibaba.fastjson.JSON;
import com.ziyao.cfx.im.api.Event;
import com.ziyao.cfx.im.api.Live;
import com.ziyao.cfx.im.api.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author ziyao zhang
 * @since 2023/6/30
 */
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<Packet> {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(ClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOGGER.info("Connection SUCCESS!");
        Channel channel = ctx.channel();
        // Send heartbeat
        channel.writeAndFlush(new Packet(Event.HEARTBEAT, Live.PING));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        LOGGER.info(JSON.toJSONString(packet));
        Packet send = new Packet("hello server");
        ctx.channel().writeAndFlush(send);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
