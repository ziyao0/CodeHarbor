package com.harbor.boot.autoconfigure.redis.config;

import com.harbor.boot.autoconfigure.redis.support.Streamable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
public interface ImplementationDetectionConfiguration {

    /**
     * Returns the postfix to be used to calculate the implementation type's name.
     *
     * @return must not be {@literal null}.
     */
    String getImplementationPostfix();

    /**
     * Return the base packages to be scanned for implementation types.
     *
     * @return must not be {@literal null}.
     */
    Streamable<String> getBasePackages();

    /**
     * Returns the exclude filters to be used for the implementation class scanning.
     *
     * @return must not be {@literal null}.
     */
    Streamable<TypeFilter> getExcludeFilters();

    /**
     * Returns the {@link MetadataReaderFactory} to be used for implementation class scanning.
     *
     * @return must not be {@literal null}.
     */
    MetadataReaderFactory getMetadataReaderFactory();

    /**
     * Generate the bean name for the given {@link BeanDefinition}.
     *
     * @param definition must not be {@literal null}.
     */
    default String generateBeanName(BeanDefinition definition) {

        Assert.notNull(definition, "BeanDefinition must not be null");

        String beanName = definition.getBeanClassName();

        if (beanName == null) {
            throw new IllegalStateException("Cannot generate bean name for BeanDefinition without bean class name");
        }

        return Introspector.decapitalize(ClassUtils.getShortName(beanName));
    }

    /**
     * Returns the final lookup configuration for the given fully-qualified fragment interface name.
     *
     * @param fragmentInterfaceName must not be {@literal null} or empty.
     */
    default ImplementationLookupConfiguration forFragment(String fragmentInterfaceName) {

        Assert.hasText(fragmentInterfaceName, "Fragment interface name must not be null or empty");

        return new DefaultImplementationLookupConfiguration(this, fragmentInterfaceName,
                Introspector.decapitalize(ClassUtils.getShortName(fragmentInterfaceName).concat(getImplementationPostfix())));
    }

    /**
     * Returns the final lookup configuration for the given {@link org.springframework.data.repository.config.RepositoryConfiguration}.
     *
     * @param config must not be {@literal null}.
     */
    default ImplementationLookupConfiguration forRepositoryConfiguration(RepositoryConfiguration<?> config) {

        Assert.notNull(config, "RepositoryConfiguration must not be null");

        return new DefaultImplementationLookupConfiguration(this, config.getRepositoryInterface(),
                config.getImplementationBeanName()) {

            @Override
            public @NonNull Streamable<String> getBasePackages() {
                return config.getImplementationBasePackages();
            }
        };
    }
}
