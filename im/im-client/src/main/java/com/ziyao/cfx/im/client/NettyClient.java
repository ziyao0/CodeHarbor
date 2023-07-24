package com.ziyao.cfx.im.client;

import com.ziyao.cfx.im.api.Packet;
import com.ziyao.cfx.im.client.core.ClientHandler;
import com.ziyao.cfx.im.client.core.PacketReceiver;
import com.ziyao.cfx.im.codec.PacketDecoder;
import com.ziyao.cfx.im.codec.PacketEncoder;
import com.ziyao.cfx.im.core.AbstractStarter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
@Slf4j
public class NettyClient extends AbstractStarter {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(NettyClient.class);
    private static final AtomicInteger MISSIONS_RETRIED = new AtomicInteger(0);
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
                .handler(new SocketChannelInitializer(clientHandler));
    }


    private static class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {
        private final ClientHandler clientHandler;

        public SocketChannelInitializer(ClientHandler clientHandler) {
            this.clientHandler = clientHandler;
        }

        @Override
        protected void initChannel(SocketChannel ch) {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new PacketDecoder());
            pipeline.addLast(new PacketEncoder());
            pipeline.addLast(clientHandler);
        }
    }


    @Override
    public void start() {
        try {
            String ip = super.fetchIP(serverAddr);
            int port = super.fetchPort(serverAddr);
            ChannelFuture channelFuture = getBootstrap().connect(ip, port);
            channelFuture.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    LOGGER.info("Netty服务端地址：{}:{}", ip, port);
                    MISSIONS_RETRIED.set(0);
                    LOGGER.info("Netty连接服务成功！");
                } else {
                    future.channel().eventLoop().schedule(() -> {
                        try {
                            int retryCount = MISSIONS_RETRIED.incrementAndGet();
                            LOGGER.error("向服务端发起重连！当前重新连接次数: {}", retryCount);
                            start();
                        } catch (Exception e) {
                            LOGGER.error("连接异常!", e);
                        }
                    }, getRetryInterval(), TimeUnit.MILLISECONDS);
                }
            });
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取重试间隔时间
     *
     * @return 返回重试时间
     */
    private static long getRetryInterval() {
        // Math.
        if (MISSIONS_RETRIED.get() <= 10) {
            return 5000;
        } else if (MISSIONS_RETRIED.get() <= 30) {
            return 30000;
        } else {
            return 60000;
        }
    }


    public static void main(String[] args) {
        new NettyClient("127.0.0.1:8888", new PacketReceiver() {
            @Override
            public void onReceive(Packet packet, Channel channel) {
                log.info("接收到的消息：{}", packet.getData());
            }
        }).start();
    }
}
