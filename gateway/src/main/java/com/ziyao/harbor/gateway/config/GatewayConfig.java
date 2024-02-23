package com.ziyao.harbor.gateway.config;

import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
@Data
@Configuration
@ConfigurationProperties("config.gateway")
public class GatewayConfig {
    /**
     * 是否跳过授权，默认不跳过
     */
    private boolean skip;
    /**
     * 默认跳过授权api集合
     */
    private Set<String> defaultSkipApis = Sets.newHashSet();
    /**
     * 跳过授权
     */
    private Set<String> skipApis = Sets.newHashSet();
    /**
     * 禁止访问api集合
     */
    private Set<String> disallowApis = Sets.newHashSet();
    /**
     * 默认禁止访问api集合
     */
    private Set<String> defaultDisallowApis = Sets.newHashSet();

}
