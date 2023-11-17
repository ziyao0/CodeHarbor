package com.ziyao.harbor.elastic;

import com.ziyao.harbor.elastic.config.EnableESRepositories;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.data.repository.config.RepositoryConfigurationDelegate;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.data.util.Streamable;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;

/**
 * @author ziyao zhang
 * @since 2023/11/17
 */
public class ESRepositoriesRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanFactoryAware, ResourceLoaderAware {

    private Environment environment;
    private ResourceLoader resourceLoader;

    private BeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata metadata, @NonNull BeanDefinitionRegistry registry,
                                        @NonNull BeanNameGenerator beanNameGenerator) {
        RepositoryConfigurationDelegate delegate = new RepositoryConfigurationDelegate(
                getConfigurationSource(registry, beanNameGenerator), this.resourceLoader, this.environment);
        delegate.registerRepositoriesIn(registry, getRepositoryConfigurationExtension());
    }


    @EnableESRepositories
    private static class EnableESRepositoriesConfiguration {

    }

    private AnnotationRepositoryConfigurationSource getConfigurationSource(BeanDefinitionRegistry registry,
                                                                           BeanNameGenerator importBeanNameGenerator) {
        AnnotationMetadata metadata = AnnotationMetadata.introspect(getConfiguration());
        return new AutoConfiguredAnnotationRepositoryConfigurationSource(metadata, getAnnotation(), this.resourceLoader,
                this.environment, registry, importBeanNameGenerator) {
        };
    }

    private class AutoConfiguredAnnotationRepositoryConfigurationSource
            extends AnnotationRepositoryConfigurationSource {

        AutoConfiguredAnnotationRepositoryConfigurationSource(AnnotationMetadata metadata,
                                                              Class<? extends Annotation> annotation, ResourceLoader resourceLoader, Environment environment,
                                                              BeanDefinitionRegistry registry, BeanNameGenerator generator) {
            super(metadata, annotation, resourceLoader, environment, registry, generator);
        }

        @NonNull
        @Override
        public Streamable<String> getBasePackages() {
            return ESRepositoriesRegistrar.this.getBasePackages();
        }

        @NonNull
        @Override
        public BootstrapMode getBootstrapMode() {
            return BootstrapMode.DEFAULT;
        }

    }


    protected Streamable<String> getBasePackages() {
        return Streamable.of(AutoConfigurationPackages.get(this.beanFactory));
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setResourceLoader(@NonNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    protected Class<? extends Annotation> getAnnotation() {
        return EnableESRepositories.class;
    }

    protected Class<?> getConfiguration() {
        return ESRepositoriesRegistrar.EnableESRepositoriesConfiguration.class;
    }

    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new ESRepositoryConfigExtension();
    }

}
