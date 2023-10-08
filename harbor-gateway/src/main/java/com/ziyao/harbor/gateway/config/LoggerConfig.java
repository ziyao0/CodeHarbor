package com.ziyao.harbor.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ziyao zhang
 * @since 2023/10/7
 */
@Data
@Configuration
@ConfigurationProperties("logging.level")
public class LoggerConfig {

    private String root;

    private boolean filterWatch = true;
}
