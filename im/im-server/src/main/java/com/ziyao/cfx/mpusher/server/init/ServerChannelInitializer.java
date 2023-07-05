package com.ziyao.cfx.mpusher.server.init;

import com.ziyao.cfx.mpusher.codec.PacketDecoder;
import com.ziyao.cfx.mpusher.codec.PacketEncoder;
import com.ziyao.cfx.mpusher.server.adapter.ShareInboundHandlerAdapter;
import com.ziyao.cfx.mpusher.server.adapter.TCPHandlerAdapter;
import com.ziyao.cfx.mpusher.server.core.MessageDispatchHolder;
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
        pipeline.addLast(new ShareInboundHandlerAdapter());
        pipeline.addLast(new TCPHandlerAdapter(new MessageDispatchHolder()));
    }



}
