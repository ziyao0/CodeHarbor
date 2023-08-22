package com.ziyao.harbor.mq.config;

import com.ziyao.harbor.mq.api.MQType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;

/**
 * @author ziyao zhang
 * @since 2023/6/8
 */
@Data
@ConfigurationProperties(value = "config")
public class MQConfig {

    /**
     * 类型
     */
    private MQType mqType = MQType.ROCKET_MQ;

    /**
     * rocketMQ相关配置
     */
    @Lazy
    @Autowired
    private RocketMQConfig rocketMQ;

    /**
     * kafka相关配置
     */
    @Lazy
    @Autowired
    private KafkaConfig kafka;

    /**
     * rabbitMQ相关配置
     */
    @Lazy
    @Autowired
    private RabbitMQConfig rabbitMQ;
}
