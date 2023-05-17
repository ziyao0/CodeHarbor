package com.cfx.web;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.cfx.web.orm.MetaFillDataHandler;
import com.cfx.web.utils.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

/**
 * <p>
 * 记录一个springboot3.x踩坑，他移除了原来的<code>spring.factories</code>方式加载外部bean，
 * 改用类似于<code>java spi</code>的形式
 * <p>
 * 现在文件的路径
 * META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
 * </p>
 *
 * @author ziyao zhang
 * @since 2023/4/26
 */
@Slf4j
@Configuration
public class AutoWebConfiguration implements ApplicationContextAware, InitializingBean {

    @Bean
    public MetaFillDataHandler metaFillDataHandler() {
        return new MetaFillDataHandler();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.setApplicationContext(applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("AutoWebConfiguration initialization.");
    }
}
