package com.cfx.usercenter.comm.properties;

import com.cfx.usercenter.security.support.SecurityUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Eason
 * @since 2023/5/9
 */
@Data
@Configuration
@ConfigurationProperties("security.config")
public class SecurityProperties {

    private String oauth2Security = SecurityUtils.OAUTH2_SECURITY;
}
