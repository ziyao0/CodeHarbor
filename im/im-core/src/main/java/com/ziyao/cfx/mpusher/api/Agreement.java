package com.ziyao.cfx.mpusher.api;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public enum Agreement {
    /**
     * * In {@code WS} SLA, websocket protocol type, used to distinguish between protocols
     */
    WS,
    /**
     * * In {@code HTTP} SLA, HTTP protocol type
     */
    HTTP,
    /**
     * * In {@code TCP} SLA, TCP protocol type,Used to identify the TCP connection
     */
    TCP,
    /**
     * * In {@code UNKNOWN} SLA, Unknown agreement
     */
    UNKNOWN,
    ;

    /**
     * Get agreement type
     *
     * @param msg message content
     * @return sla
     * @see Agreement
     */
    public static Agreement getInstance(Object msg) {
        if (msg instanceof ByteBuf) {
            return TCP;
        } else if (msg instanceof TextWebSocketFrame) {
            return WS;
        } else if (msg instanceof HttpContent || msg instanceof HttpRequest) {
            return HTTP;
        } else {
            return UNKNOWN;
        }
    }
}
