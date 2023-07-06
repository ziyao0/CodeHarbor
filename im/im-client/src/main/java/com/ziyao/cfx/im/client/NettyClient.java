package com.ziyao.cfx.im.client;

import com.ziyao.cfx.im.api.Packet;
import com.ziyao.cfx.im.client.core.ClientHandler;
import com.ziyao.cfx.im.client.core.PacketReceiver;
import com.ziyao.cfx.im.codec.PacketDecoder;
import com.ziyao.cfx.im.codec.PacketEncoder;
import com.ziyao.cfx.im.core.AbstractStarter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
@Slf4j
public class NettyClient extends AbstractStarter {

//    private static final Logger LOGGER =

    private final String serverAddr;

    private final ClientHandler clientHandler;

    public NettyClient(String serverAddr, PacketReceiver packetReceiver) {
        super(new Bootstrap());
        init();
        this.serverAddr = serverAddr;
        clientHandler = new ClientHandler(packetReceiver, this);
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
                        pipeline.addLast(clientHandler);
                    }
                });
    }


    public void start() throws UnknownHostException {
        String ip = super.fetchIP(serverAddr);
        int port = super.fetchPort(serverAddr);
        SocketAddress socketAddress = new InetSocketAddress(ip, port);
        super.start(socketAddress);
    }

    public static void main(String[] args) throws UnknownHostException {
        new NettyClient("127.0.0.1:8888", new PacketReceiver() {
            @Override
            public void onReceive(Packet packet, Channel channel) {
                log.info("接收到的消息：{}", packet);
            }
        }).start();
    }
}
