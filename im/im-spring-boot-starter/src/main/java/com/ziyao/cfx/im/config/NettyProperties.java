package com.ziyao.cfx.im.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ziyao zhang
 * @since 2023/7/6
 */
@Data
@ConfigurationProperties("ziyao.netty")
public class NettyProperties {

    private String serverAddr;
}
