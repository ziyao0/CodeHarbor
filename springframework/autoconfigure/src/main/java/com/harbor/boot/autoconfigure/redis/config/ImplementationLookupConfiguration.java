package com.harbor.boot.autoconfigure.redis.config;

import org.springframework.beans.factory.config.BeanDefinition;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
public interface ImplementationLookupConfiguration extends ImplementationDetectionConfiguration {

    /**
     * Returns the bean name of the implementation to be looked up.
     *
     * @return must not be {@literal null}.
     */
    String getImplementationBeanName();

    /**
     * Returns the simple name of the class to be looked up.
     *
     * @return must not be {@literal null}.
     */
    String getImplementationClassName();

    /**
     * Return whether the given {@link BeanDefinition} matches the lookup configuration.
     *
     * @param definition must not be {@literal null}.
     */
    boolean matches(BeanDefinition definition);

    /**
     * Returns whether the bean name created for the given bean definition results in the one required. Will be used to
     * disambiguate between multiple {@link BeanDefinition}s matching in general.
     *
     * @param definition must not be {@literal null}.
     * @see #matches(BeanDefinition)
     */
    boolean hasMatchingBeanName(BeanDefinition definition);
}
