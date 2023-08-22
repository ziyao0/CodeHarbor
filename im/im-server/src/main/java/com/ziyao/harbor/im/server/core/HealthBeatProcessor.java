package com.ziyao.harbor.im.server.core;

import com.alibaba.fastjson.JSON;
import com.ziyao.harbor.im.api.Agreement;
import com.ziyao.harbor.im.api.Event;
import com.ziyao.harbor.im.api.Live;
import com.ziyao.harbor.im.api.Packet;
import com.ziyao.harbor.im.core.MetricsProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public class HealthBeatProcessor implements MetricsProcessor {


    /**
     * Read the data sent by the client
     *
     * @param ctx       Context object, containing channels{@link io.netty.channel.Channel}
     *                  channel{@link io.netty.channel.ChannelPipeline}
     * @param agreement agreement type
     */
    @Override
    public void process(ChannelHandlerContext ctx, Agreement agreement) {
        // Async respond to Netty Client heartbeat requests
        ctx.channel().eventLoop().submit(() -> {
            // send heart beat
            try {
                Thread.sleep(5000L);
                Packet heartbeat = new Packet(Event.HEARTBEAT, Live.PONG);
                if (agreement.equals(Agreement.TCP)) {
                    ctx.channel().writeAndFlush(heartbeat);
                } else {
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(heartbeat)));
                }
                //设置用户在线
                LOGGER.debug("触发心跳.");

            } catch (InterruptedException e) {
                LOGGER.error("Netty Server传输心跳中断异常！{}", e.getMessage());
            }
        });
    }
}
