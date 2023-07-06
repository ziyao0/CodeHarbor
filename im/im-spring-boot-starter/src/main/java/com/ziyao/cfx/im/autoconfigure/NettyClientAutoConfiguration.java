package com.ziyao.cfx.im.autoconfigure;

import com.ziyao.cfx.im.client.NettyClient;
import com.ziyao.cfx.im.client.core.PacketReceiver;
import com.ziyao.cfx.im.config.NettyProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author ziyao zhang
 * @since 2023/7/6
 */
@Configuration
@Import(NettyProperties.class)
public class NettyClientAutoConfiguration {

    @Bean(initMethod = "start", destroyMethod = "close")
    @ConditionalOnBean(PacketReceiver.class)
    public NettyClient nettyClient(NettyProperties nettyProperties, PacketReceiver packetReceiver) {
        return new NettyClient(nettyProperties.getServerAddr(), packetReceiver);
    }
}
