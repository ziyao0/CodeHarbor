package com.cfx.gateway.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Eason
 * @since 2023/5/16
 */
@Data
@Configuration
@ConfigurationProperties("config.gateway")
public class GatewayConfig {


    private String oauth2Security = "cfx:oauth2:security";
}
