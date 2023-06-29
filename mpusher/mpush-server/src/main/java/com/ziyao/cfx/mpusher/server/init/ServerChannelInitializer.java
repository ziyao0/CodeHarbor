package com.ziyao.cfx.mpusher.server.init;

import com.ziyao.cfx.mpusher.core.PipelineHolder;
import com.ziyao.cfx.mpusher.server.adapter.ShareInboundHandlerAdapter;
import com.ziyao.cfx.mpusher.server.adapter.TCPHandlerAdapter;
import com.ziyao.cfx.mpusher.server.core.MessageDispatchHolder;
import io.netty.channel.ChannelInitializer;
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

        PipelineHolder.createPipeline(channel);
        PipelineHolder.addLast(new ShareInboundHandlerAdapter());
        PipelineHolder.addLast(new TCPHandlerAdapter(new MessageDispatchHolder()));
    }
}
