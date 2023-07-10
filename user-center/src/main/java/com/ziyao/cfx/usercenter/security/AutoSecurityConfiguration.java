package com.ziyao.cfx.usercenter.security;

import com.ziyao.cfx.usercenter.security.core.PrimaryAuthProvider;
import com.ziyao.cfx.web.utils.ApplicationContextUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@Configuration
public class AutoSecurityConfiguration implements ApplicationContextAware {

    @Bean
    public PrimaryAuthProviderManager primaryAuthProviderManager() {
        List<PrimaryAuthProvider> beans = ApplicationContextUtils.getBeansOfType(PrimaryAuthProvider.class);
        return new PrimaryAuthProviderManager(beans, null);
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.setApplicationContext(applicationContext);
    }
}
