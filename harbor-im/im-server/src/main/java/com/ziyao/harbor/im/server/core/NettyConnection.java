package com.ziyao.harbor.im.server.core;

import com.ziyao.harbor.im.api.Connection;
import com.ziyao.harbor.im.api.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author ziyao zhang
 * @since 2023/7/3
 */
public class NettyConnection implements Connection, ChannelFutureListener {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(NettyConnection.class);


    private String deviceId;
    public String userId;
    private Channel channel;

    @Override
    public void init(Channel channel) {
        this.channel = channel;
    }

    @Override
    public ChannelFuture send(Packet packet) {
        return send(packet, null);
    }

    @Override
    public ChannelFuture send(Packet packet, ChannelFutureListener listener) {
        if (channel.isActive()) {
            ChannelFuture future = channel.writeAndFlush(packet).addListener(this);

            if (listener != null) {
                future.addListener(listener);
            }

            if (channel.isWritable()) {
                return future;
            }
            //阻塞调用线程还是抛异常？
//            return channel.newPromise().setFailure(new RuntimeException("send data too busy"));
            if (!future.channel().eventLoop().inEventLoop()) {
                future.awaitUninterruptibly(100);
            }
            return future;
        } else {
             /*if (listener != null) {
                channel.newPromise()
                        .addListener(listener)
                        .setFailure(new RuntimeException("connection is disconnected"));
            }*/
            return this.close();
        }
    }

    @Override
    public String getId() {
        return channel.id().asShortText();
    }

    @Override
    public ChannelFuture close() {
        return this.channel.close();
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            LOGGER.error("write success !");
        } else {
            LOGGER.error("connection send msg error", future.cause());
        }
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
