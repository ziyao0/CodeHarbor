package com.ziyao.cfx.im.server.init;

import com.ziyao.cfx.im.codec.PacketDecoder;
import com.ziyao.cfx.im.codec.PacketEncoder;
import com.ziyao.cfx.im.server.adapter.ShareHandlerAdapter;
import com.ziyao.cfx.im.server.adapter.TCPHandlerAdapter;
import com.ziyao.cfx.im.server.core.MessageDispatchHolder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.NonNull;

/**
 * Netty Server Channel initializer {@link ChannelInitializer<SocketChannel>}
 *
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(@NonNull SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
//        PipelineHolder.createPipeline(channel);
        pipeline.addLast(new PacketDecoder());
        pipeline.addLast(new PacketEncoder());
        pipeline.addLast(new ShareHandlerAdapter());
        pipeline.addLast(new TCPHandlerAdapter(new MessageDispatchHolder()));
    }



}
