package com.harbor.boot.autoconfigure.redis.config;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.util.Assert;


/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
public abstract class RepositoryConfigurationUtils {
    public static final String GENERATED_BEAN_NAME_SEPARATOR = BeanFactoryUtils.GENERATED_BEAN_NAME_SEPARATOR;

    /**
     * Registers the given {@link RepositoryConfigurationExtension} to indicate the repository configuration for a
     * particular store (expressed through the extension's concrete type) has happened. Useful for downstream components
     * that need to detect exactly that case. The bean definition is marked as lazy-init so that it doesn't get
     * instantiated if no one really cares.
     *
     * @param extension           must not be {@literal null}.
     * @param registry            must not be {@literal null}.
     * @param configurationSource must not be {@literal null}.
     */
    public static void exposeRegistration(RepositoryConfigurationExtension extension, BeanDefinitionRegistry registry,
                                          RepositoryConfigurationSource configurationSource) {

        Assert.notNull(extension, "RepositoryConfigurationExtension must not be null");
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
        Assert.notNull(configurationSource, "RepositoryConfigurationSource must not be null");

        Class<? extends RepositoryConfigurationExtension> extensionType = extension.getClass();
        String beanName = extensionType.getName().concat(GENERATED_BEAN_NAME_SEPARATOR).concat("0");

        if (registry.containsBeanDefinition(beanName)) {
            return;
        }

        // Register extension as bean to indicate repository parsing and registration has happened
        RootBeanDefinition definition = new RootBeanDefinition(extensionType);
        definition.setSource(configurationSource.getSource());
        definition.setRole(AbstractBeanDefinition.ROLE_INFRASTRUCTURE);
        definition.setLazyInit(true);

        registry.registerBeanDefinition(beanName, definition);
    }
}
