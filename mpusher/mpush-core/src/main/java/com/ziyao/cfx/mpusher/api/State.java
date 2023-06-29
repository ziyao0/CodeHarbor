package com.ziyao.cfx.mpusher.api;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public enum State {

    /**
     * In {@code ACK} state,When in the current state, all messages are considered to execute the logic of ack
     */
    ACK,
    /**
     * In {@code SEND} state, The identity of the server sending a message to the client
     */
    SEND,
    /**
     * In {@code OPEN} state, Connection establishment The identifier sent by the web to the server to establish a connection
     */
    OPEN,
    /**
     * In {@code CLOSE} state, Close the connection ID
     */
    CLOSE,
    /**
     * In {@code PING} state, The heartbeat code of the server sent by the web
     */
    PING,
    /**
     * In {@code PONG} state, Heartbeat code sent by the server to the web
     */
    PONG,
}
