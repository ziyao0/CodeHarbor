package com.ziyao.cfx.mpusher.client.core;

import com.alibaba.fastjson.JSON;
import com.ziyao.cfx.mpusher.api.Live;
import com.ziyao.cfx.mpusher.api.Packet;
import com.ziyao.cfx.mpusher.api.State;
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
        channel.writeAndFlush(new Packet(State.PING, Live.PING));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info(JSON.toJSONString(msg));
        Packet packet = new Packet("hello server");
        ctx.channel().writeAndFlush(packet);
    }
}