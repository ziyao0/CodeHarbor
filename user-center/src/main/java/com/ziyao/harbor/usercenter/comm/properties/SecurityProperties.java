package com.ziyao.harbor.usercenter.comm.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ziyao zhang
 * @since 2023/5/9
 */
@Data
@Configuration
@ConfigurationProperties("security.config")
public class SecurityProperties {

}
