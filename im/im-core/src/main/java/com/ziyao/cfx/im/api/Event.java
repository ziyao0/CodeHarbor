package com.ziyao.cfx.im.api;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public enum Event {

    /**
     * In {@code OPEN} Event, Connection establishment The identifier sent by the web to the server to establish a connection
     */
    OPEN,
    /**
     * In {@code HEARTBEAT} Event,Heartbeat events
     */
    HEARTBEAT,
    /**
     * In {@code SEND} Event, The identity of the server sending a message to the client
     */
    SEND,
    /**
     * In {@code ACK} Event,When in the current Event, all messages are considered to execute the logic of ack
     */
    ACK,
    /**
     * In {@code CLOSE} Event, Close the connection ID
     */
    CLOSE,
}
