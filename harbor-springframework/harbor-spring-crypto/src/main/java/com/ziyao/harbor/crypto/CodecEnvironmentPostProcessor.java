package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.crypto.core.CipherContext;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public class CodecEnvironmentPostProcessor extends AbstractCodecEnvironment
        implements EnvironmentPostProcessor, ApplicationContextAware, Ordered {

    private CipherContext context;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        environment.getPropertySources().remove(CIPHER_PROPERTY_SOURCE_NAME);
        Map<String, Object> properties = decrypt(context, propertySources);
        if (!Collections.isEmpty(properties)) {
            environment.getPropertySources().addFirst(
                    new SystemEnvironmentPropertySource(
                            CIPHER_PROPERTY_SOURCE_NAME, properties)
            );
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext.getBean(CipherContext.class);
    }
}
