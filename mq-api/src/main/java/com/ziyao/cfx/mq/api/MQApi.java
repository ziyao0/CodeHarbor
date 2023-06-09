package com.ziyao.cfx.mq.api;

/**
 * @author ziyao zhang
 * @since 2023/6/1
 */
public interface MQApi {

    void send(String topic, Message message);
}
