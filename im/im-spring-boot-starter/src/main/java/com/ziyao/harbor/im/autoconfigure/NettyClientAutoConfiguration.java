package com.ziyao.harbor.im.autoconfigure;

import com.ziyao.harbor.im.client.NettyClient;
import com.ziyao.harbor.im.client.core.PacketReceiver;
import com.ziyao.harbor.im.config.NettyProperties;
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
