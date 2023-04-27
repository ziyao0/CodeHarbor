package com.cfx.web;

import com.cfx.web.utils.ApplicationContextUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

/**
 * @author Eason
 * @since 2023/4/26
 */
@Configuration
public class AutoWebConfiguration implements ApplicationContextAware {


    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.setApplicationContext(applicationContext);
    }

}
