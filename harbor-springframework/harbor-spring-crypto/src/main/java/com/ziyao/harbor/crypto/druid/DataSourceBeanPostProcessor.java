package com.ziyao.harbor.crypto.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.ziyao.harbor.core.lang.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.sql.DataSource;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public class DataSourceBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof DruidDataSource) {
            if (bean instanceof InitializingBean) {
                try {
                    ((InitializingBean) bean).afterPropertiesSet();
                } catch (Exception e) {
                    throw new BeanCreationException("create DruidDataSource invoke afterPropertiesSet error.", e);
                }
            }
            DruidDataSourceWrapper.init((DataSource) bean);
        }
        return bean;
    }
}
