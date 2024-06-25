package com.harbor.boot.autoconfigure.redis.config;

import com.harbor.boot.autoconfigure.redis.support.Streamable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
public interface RepositoryConfigurationSource {

    @Nullable
    Object getSource();

    Streamable<String> getBasePackages();

    Optional<Object> getQueryLookupStrategyKey();

    Optional<String> getRepositoryImplementationPostfix();

    Optional<String> getNamedQueryLocation();

    Optional<String> getRepositoryBaseClassName();

    Optional<String> getRepositoryFactoryBeanClassName();

    Streamable<BeanDefinition> getCandidates(ResourceLoader loader);

    Optional<String> getAttribute(String name);

    <T> Optional<T> getAttribute(String name, Class<T> type);

    default <T> T getRequiredAttribute(String name, Class<T> type) {

        Assert.hasText(name, "Attribute name must not be null or empty");

        return getAttribute(name, type)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No attribute named %s found", name)));
    }

    boolean usesExplicitFilters();

    Streamable<TypeFilter> getExcludeFilters();

    String generateBeanName(BeanDefinition beanDefinition);

    ImplementationDetectionConfiguration toImplementationDetectionConfiguration(MetadataReaderFactory factory);

    BootstrapMode getBootstrapMode();

    @Nullable
    String getResourceDescription();
}
