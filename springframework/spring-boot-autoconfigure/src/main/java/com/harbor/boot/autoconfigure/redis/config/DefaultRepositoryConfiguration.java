package com.harbor.boot.autoconfigure.redis.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.config.ConfigurationUtils;
import org.springframework.data.repository.config.BootstrapMode;

import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.util.Lazy;
import org.springframework.data.util.Streamable;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
public class DefaultRepositoryConfiguration<T extends RepositoryConfigurationSource>
        implements RepositoryConfiguration<T> {

    public static final String DEFAULT_REPOSITORY_IMPLEMENTATION_POSTFIX = "Impl";
    public static final QueryLookupStrategy.Key DEFAULT_QUERY_LOOKUP_STRATEGY = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND;

    private final T configurationSource;
    private final BeanDefinition definition;
    private final RepositoryConfigurationExtension extension;
    private final Lazy<String> beanName;

    public DefaultRepositoryConfiguration(T configurationSource, BeanDefinition definition,
                                          RepositoryConfigurationExtension extension) {

        this.configurationSource = configurationSource;
        this.definition = definition;
        this.extension = extension;
        this.beanName = Lazy.of(() -> configurationSource.generateBeanName(definition));
    }

    public String getBeanId() {
        return StringUtils.uncapitalize(ClassUtils.getShortName(getRepositoryBaseClassName().orElseThrow(
                () -> new IllegalStateException("Can't create bean identifier without a repository base class defined"))));
    }

    public Object getQueryLookupStrategyKey() {
        return configurationSource.getQueryLookupStrategyKey().orElse(DEFAULT_QUERY_LOOKUP_STRATEGY);
    }

    public Streamable<String> getBasePackages() {
        return configurationSource.getBasePackages();
    }

    @Override
    public Streamable<String> getImplementationBasePackages() {
        return Streamable.of(ClassUtils.getPackageName(getRepositoryInterface()));
    }

    public String getRepositoryInterface() {
        return ConfigurationUtils.getRequiredBeanClassName(definition);
    }

    public RepositoryConfigurationSource getConfigSource() {
        return configurationSource;
    }

    public Optional<String> getNamedQueriesLocation() {
        return configurationSource.getNamedQueryLocation();
    }

    public String getImplementationClassName() {
        return ClassUtils.getShortName(getRepositoryInterface()).concat(
                configurationSource.getRepositoryImplementationPostfix().orElse(DEFAULT_REPOSITORY_IMPLEMENTATION_POSTFIX));
    }

    public String getImplementationBeanName() {
        return beanName.get() + configurationSource.getRepositoryImplementationPostfix().orElse("Impl");
    }

    @Nullable
    @Override
    public Object getSource() {
        return configurationSource.getSource();
    }

    @Override
    public T getConfigurationSource() {
        return configurationSource;
    }

    @Override
    public Optional<String> getRepositoryBaseClassName() {
        return configurationSource.getRepositoryBaseClassName();
    }

    @Override
    public String getRepositoryFactoryBeanClassName() {

        return configurationSource.getRepositoryFactoryBeanClassName()
                .orElseGet(extension::getRepositoryFactoryBeanClassName);
    }

    @Override
    public String getRepositoryBeanName() {
        return beanName.get();
    }

    @Override
    public boolean isLazyInit() {
        return definition.isLazyInit() || !configurationSource.getBootstrapMode().equals(BootstrapMode.DEFAULT);
    }

    @Override
    public boolean isPrimary() {
        return definition.isPrimary();
    }

    @Override
    public Streamable<TypeFilter> getExcludeFilters() {
        return configurationSource.getExcludeFilters();
    }

    @Override
    public ImplementationDetectionConfiguration toImplementationDetectionConfiguration(MetadataReaderFactory factory) {

        Assert.notNull(factory, "MetadataReaderFactory must not be null");

        return configurationSource.toImplementationDetectionConfiguration(factory);
    }

    @Override
    public ImplementationLookupConfiguration toLookupConfiguration(MetadataReaderFactory factory) {

        Assert.notNull(factory, "MetadataReaderFactory must not be null");
        
        return toImplementationDetectionConfiguration(factory).forRepositoryConfiguration(this);
        
        
        
    }

    @Override
    @org.springframework.lang.NonNull
    public String getResourceDescription() {
        return String.format("%s defined in %s", getRepositoryInterface(), configurationSource.getResourceDescription());
    }
}
