package com.ziyao.cfx.mpusher.server.adapter;

import com.ziyao.cfx.mpusher.api.Agreement;
import com.ziyao.cfx.mpusher.api.Message;
import com.ziyao.cfx.mpusher.core.ChannelManager;
import com.ziyao.cfx.mpusher.server.core.HealthBeatProcessor;
import com.ziyao.cfx.mpusher.server.core.MessageDispatchHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
@ChannelHandler.Sharable
public class TCPHandlerAdapter extends SimpleChannelInboundHandler<Message> {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(TCPHandlerAdapter.class);

    private final MessageDispatchHolder messageDispatchHolder;

    public TCPHandlerAdapter(MessageDispatchHolder messageDispatchHolder) {
        this.messageDispatchHolder = messageDispatchHolder;
    }

    /**
     * 读取客户端发送的数据
     *
     * @param ctx     Context object, containing channels{@link io.netty.channel.Channel}
     *                channel{@link io.netty.channel.ChannelPipeline}
     * @param message Data sent by the client
     * @see io.netty.channel.Channel
     * @see io.netty.channel.ChannelPipeline
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) {
        LOGGER.info("TCP Server send to message is {}", message.getMsg());
        switch (message.getState()) {
            case OPEN ->
                // 请求
                    processReadEvent(ctx, (String) message.getMsg());
            case SEND -> {
                // 发送消息
                LOGGER.debug("{}", message.getMsg());

            }

            case PING -> {
                LOGGER.debug("Received heartbeat parameters:{}", message);
                //健康检查pong
                new HealthBeatProcessor().process(ctx, Agreement.TCP);
            }
            case ACK -> messageDispatchHolder.ack((String) message.getMsg());
            default -> LOGGER.error("Unknown processing status {}!", message.getState());
        }
    }


    /**
     * Store the connection between the user and the pipeline
     *
     * @param ctx    Context object, containing channels{@link io.netty.channel.Channel}
     *               channel{@link io.netty.channel.ChannelPipeline}
     * @param userId User ID
     */
    private void processReadEvent(ChannelHandlerContext ctx, String userId) {
        Channel channel = ctx.channel();
        // Asynchronously process user online retransmissions
        channel.eventLoop().submit(() -> {
            ChannelManager.storage(ctx.channel());
            //存储管道和用户id之间的关系
            //设置用户在线
            //User connection attempt message resend
            LOGGER.debug("[WebSocketServerHandler#channelRead()] The user goes online and Try to resend!userId:{}", userId);
            messageDispatchHolder.retry(userId);
        });
    }
}
