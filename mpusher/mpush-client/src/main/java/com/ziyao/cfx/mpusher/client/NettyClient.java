package com.ziyao.cfx.mpusher.client;

import com.ziyao.cfx.mpusher.client.core.ClientHandler;
import com.ziyao.cfx.mpusher.codec.PacketDecoder;
import com.ziyao.cfx.mpusher.codec.PacketEncoder;
import com.ziyao.cfx.mpusher.core.AbstractStarter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.NonNull;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class NettyClient extends AbstractStarter {
    public NettyClient() {
        super(new Bootstrap());
    }

    @Override
    public void init() {
        super.getBootstrap().group(super.getWorkGroup())
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(@NonNull SocketChannel channel) {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new PacketDecoder());
                        pipeline.addLast(new PacketEncoder());
                        pipeline.addLast(new ClientHandler());
                    }
                });
    }

    public static void main(String[] args) {
        new NettyClient().start("127.0.0.1", 8888);
    }


}
