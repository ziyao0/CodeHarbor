package com.harbor.boot.autoconfigure.redis.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.util.Streamable;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.IOException;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
class DefaultImplementationLookupConfiguration implements ImplementationLookupConfiguration {

    private final ImplementationDetectionConfiguration config;
    private final String interfaceName;
    private final String beanName;

    DefaultImplementationLookupConfiguration(ImplementationDetectionConfiguration config, String interfaceName,
                                             String beanName) {

        Assert.notNull(config, "ImplementationDetectionConfiguration must not be null");
        Assert.hasText(interfaceName, "Interface name must not be null or empty");
        Assert.hasText(beanName, "Bean name must not be null or empty");

        this.config = config;
        this.interfaceName = interfaceName;
        this.beanName = beanName;
    }

    @Override
    public @NonNull String getImplementationBeanName() {
        return beanName;
    }

    @Override
    public @NonNull String getImplementationPostfix() {
        return config.getImplementationPostfix();
    }

    @Override
    public @NonNull Streamable<TypeFilter> getExcludeFilters() {
        return config.getExcludeFilters().and(new AnnotationTypeFilter(NoRepositoryBean.class));
    }

    @Override
    public @NonNull MetadataReaderFactory getMetadataReaderFactory() {
        return config.getMetadataReaderFactory();
    }

    @Override
    public @NonNull Streamable<String> getBasePackages() {
        return Streamable.of(ClassUtils.getPackageName(interfaceName));
    }

    @Override
    public @NonNull String getImplementationClassName() {
        return getLocalName(interfaceName).concat(getImplementationPostfix());
    }

    @Override
    public boolean hasMatchingBeanName(@NonNull BeanDefinition definition) {

        Assert.notNull(definition, "BeanDefinition must not be null");

        return beanName != null && beanName.equals(config.generateBeanName(definition));
    }

    @Override
    public boolean matches(@NonNull BeanDefinition definition) {

        Assert.notNull(definition, "BeanDefinition must not be null");

        String beanClassName = definition.getBeanClassName();

        if (beanClassName == null || isExcluded(beanClassName, getExcludeFilters())) {
            return false;
        }

        String beanPackage = ClassUtils.getPackageName(beanClassName);
        String localName = getLocalName(beanClassName);

        return localName.equals(getImplementationClassName()) //
                && getBasePackages().stream().anyMatch(beanPackage::startsWith);
    }

    private String getLocalName(String className) {

        String shortName = ClassUtils.getShortName(className);
        return shortName.substring(shortName.lastIndexOf('.') + 1);
    }

    private boolean isExcluded(String beanClassName, Streamable<TypeFilter> filters) {

        try {

            MetadataReader reader = getMetadataReaderFactory().getMetadataReader(beanClassName);
            return filters.stream().anyMatch(it -> matches(it, reader));
        } catch (IOException o_O) {
            return true;
        }
    }

    private boolean matches(TypeFilter filter, MetadataReader reader) {

        try {
            return filter.match(reader, getMetadataReaderFactory());
        } catch (IOException e) {
            return false;
        }
    }
}
