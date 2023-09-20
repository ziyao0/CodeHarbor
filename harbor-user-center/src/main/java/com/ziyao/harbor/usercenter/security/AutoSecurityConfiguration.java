package com.ziyao.harbor.usercenter.security;

import com.ziyao.harbor.usercenter.security.core.PrimaryProvider;
import com.ziyao.harbor.web.ApplicationContextUtils;
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
    public PrimaryProviderManager primaryAuthProviderManager() {
        List<PrimaryProvider> beans = ApplicationContextUtils.getBeansOfType(PrimaryProvider.class);
        return new PrimaryProviderManager(beans);
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.setApplicationContext(applicationContext);
    }
}
