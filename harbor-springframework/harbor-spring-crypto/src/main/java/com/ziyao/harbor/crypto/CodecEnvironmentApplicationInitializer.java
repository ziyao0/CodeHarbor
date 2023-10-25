package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.lang.NonNull;
import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.crypto.core.CipherContext;
import com.ziyao.harbor.crypto.core.CipherContextFactory;
import org.springframework.cloud.bootstrap.BootstrapApplicationListener;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/10/24
 */
public class CodecEnvironmentApplicationInitializer
        extends AbstractCodecEnvironment
        implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    private final CipherContextFactory cipherContextFactory;

    public CodecEnvironmentApplicationInitializer(CipherContextFactory cipherContextFactory) {
        this.cipherContextFactory = cipherContextFactory;
    }

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {

        CipherContext context = cipherContextFactory.createContext(applicationContext);

        applicationContext.getBeanFactory()
                .registerSingleton(CipherContext.class.getName(), context);
        decryption(context, applicationContext);
    }

    private void decryption(CipherContext context, ConfigurableApplicationContext applicationContext) {

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        Set<String> found = new LinkedHashSet<>();

        PropertySource<?> bootstrap = propertySources.get(
                BootstrapApplicationListener.BOOTSTRAP_PROPERTY_SOURCE_NAME);
        if (bootstrap != null) {
            Map<String, Object> map = decrypt(context, bootstrap);
            if (!Collections.isEmpty(map)) {
                found.addAll(map.keySet());
                this.add(applicationContext, new SystemEnvironmentPropertySource(
                        CIPHER_BOOTSTRAP_PROPERTY_SOURCE_NAME, map));
            }
        }
        // 删除加密配置属性
        remove(applicationContext);
        Map<String, Object> map = decrypt(context, propertySources);
        if (!Collections.isEmpty(map)) {

            found.addAll(map.keySet());
            add(applicationContext, new SystemEnvironmentPropertySource(
                    CIPHER_PROPERTY_SOURCE_NAME, map));
        }
        if (!found.isEmpty()) {
            ApplicationContext parent = applicationContext.getParent();
            if (parent != null) {
                parent.publishEvent(new EnvironmentChangeEvent(parent, found));
            }
        }
    }

    /**
     * 删除加密配置
     */
    private void remove(ConfigurableApplicationContext applicationContext) {
        ApplicationContext parent = applicationContext;
        while (parent != null) {
            if (parent.getEnvironment() instanceof ConfigurableEnvironment) {
                ((ConfigurableEnvironment) parent.getEnvironment()).getPropertySources()
                        .remove(CIPHER_PROPERTY_SOURCE_NAME);
            }
            parent = parent.getParent();
        }

    }

    private void add(ConfigurableApplicationContext applicationContext,
                     PropertySource<?> propertySource) {
        ApplicationContext parent = applicationContext;
        while (parent != null) {
            if (parent.getEnvironment() instanceof ConfigurableEnvironment mutable) {
                add(mutable.getPropertySources(), propertySource);
            }
            parent = parent.getParent();
        }

    }

    private void add(MutablePropertySources propertySources,
                     PropertySource<?> propertySource) {
        if (propertySources
                .contains(BootstrapApplicationListener.BOOTSTRAP_PROPERTY_SOURCE_NAME)) {
            if (CIPHER_BOOTSTRAP_PROPERTY_SOURCE_NAME.equals(propertySource.getName())) {
                propertySources.addBefore(
                        BootstrapApplicationListener.BOOTSTRAP_PROPERTY_SOURCE_NAME,
                        propertySource);
            } else {
                propertySources.addAfter(
                        BootstrapApplicationListener.BOOTSTRAP_PROPERTY_SOURCE_NAME,
                        propertySource);
            }
        } else {
            propertySources.addFirst(propertySource);
        }
    }

    private Map<String, Object> decrypt(CipherContext context,
                                        PropertySource<?> propertySources) {
        Map<String, Object> properties = merge(context, propertySources);
        decrypt(properties);
        return properties;
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 14;
    }
}
