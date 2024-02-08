package com.ziyao.harbor.data.redis.autoconfigure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.data.repository.config.*;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author ziyao zhang
 * @since 2024/2/5
 */
public class RedisRepositoryBeanDefinitionBuilder {
    private static final Log logger = LogFactory.getLog(RedisRepositoryBeanDefinitionBuilder.class);

    private final BeanDefinitionRegistry registry;
    private final RepositoryConfigurationExtension extension;
    private final ResourceLoader resourceLoader;

    private final MetadataReaderFactory metadataReaderFactory;
    private final CustomRepositoryImplementationDetector implementationDetector;

    /**
     * Creates a new {@link RedisRepositoryBeanDefinitionBuilder} from the given {@link BeanDefinitionRegistry},
     * {@link RepositoryConfigurationExtension} and {@link ResourceLoader}.
     *
     * @param registry       must not be {@literal null}.
     * @param extension      must not be {@literal null}.
     * @param resourceLoader must not be {@literal null}.
     * @param environment    must not be {@literal null}.
     */
    public RedisRepositoryBeanDefinitionBuilder(BeanDefinitionRegistry registry, RepositoryConfigurationExtension extension,
                                                RepositoryConfigurationSource configurationSource, ResourceLoader resourceLoader, Environment environment) {

        Assert.notNull(extension, "RepositoryConfigurationExtension must not be null");
        Assert.notNull(resourceLoader, "ResourceLoader must not be null");
        Assert.notNull(environment, "Environment must not be null");

        this.registry = registry;
        this.extension = extension;
        this.resourceLoader = resourceLoader;

        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);

        this.implementationDetector = new CustomRepositoryImplementationDetector(environment, resourceLoader,
                configurationSource.toImplementationDetectionConfiguration(metadataReaderFactory));
    }

    /**
     * Builds a new {@link BeanDefinitionBuilder} from the given {@link BeanDefinitionRegistry} and {@link ResourceLoader}
     * .
     *
     * @param configuration must not be {@literal null}.
     */
    public BeanDefinitionBuilder build(RepositoryConfiguration<?> configuration) {

        Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
        Assert.notNull(resourceLoader, "ResourceLoader must not be null");

        BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .rootBeanDefinition(configuration.getRepositoryFactoryBeanClassName());

        builder.getRawBeanDefinition().setSource(configuration.getSource());
        builder.addConstructorArgValue(configuration.getRepositoryInterface());
        builder.addPropertyValue("lazyInit", configuration.isLazyInit());
        builder.setLazyInit(configuration.isLazyInit());
        builder.setPrimary(configuration.isPrimary());

        configuration.getRepositoryBaseClassName()//
                .ifPresent(it -> builder.addPropertyValue("repositoryBaseClass", it));

        registerCustomImplementation(configuration).ifPresent(it -> {
            builder.addPropertyReference("customImplementation", it);
            builder.addDependsOn(it);
        });


        return builder;
    }

    private Optional<String> registerCustomImplementation(RepositoryConfiguration<?> configuration) {

        ImplementationLookupConfiguration lookup = configuration.toLookupConfiguration(metadataReaderFactory);

        String configurationBeanName = lookup.getImplementationBeanName();

        // Already a bean configured?
        if (registry.containsBeanDefinition(configurationBeanName)) {

            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Custom repository implementation already registered: %s", configurationBeanName));
            }

            return Optional.of(configurationBeanName);
        }

        return implementationDetector.detectCustomImplementation(lookup)
                .map(it -> potentiallyRegisterRepositoryImplementation(configuration, it));
    }


    private String potentiallyRegisterRepositoryImplementation(RepositoryConfiguration<?> configuration,
                                                               AbstractBeanDefinition beanDefinition) {

        String targetBeanName = configuration.getConfigurationSource().generateBeanName(beanDefinition);
        beanDefinition.setSource(configuration.getSource());

        if (registry.containsBeanDefinition(targetBeanName)) {

            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Custom repository implementation already registered: %s %s", targetBeanName,
                        beanDefinition.getBeanClassName()));
            }
        } else {

            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Registering custom repository implementation: %s %s", targetBeanName,
                        beanDefinition.getBeanClassName()));
            }

            registry.registerBeanDefinition(targetBeanName, beanDefinition);
        }

        return targetBeanName;
    }
}
