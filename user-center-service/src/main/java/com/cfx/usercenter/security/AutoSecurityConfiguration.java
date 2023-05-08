package com.cfx.usercenter.security;

import com.cfx.usercenter.security.core.PrimaryAuthProvider;
import com.cfx.web.utils.ApplicationContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@Configuration
public class AutoSecurityConfiguration {

    @Bean
    public PrimaryAuthProviderManager primaryAuthProviderManager(){
        List<PrimaryAuthProvider> beans = ApplicationContextUtils.getBeansOfType(PrimaryAuthProvider.class);
        return new PrimaryAuthProviderManager(beans,null);
    }
}
