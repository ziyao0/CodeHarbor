package com.harbor.boot.autoconfigure.redis;

import com.harbor.boot.autoconfigure.redis.config.AnnotationRepositoryConfigurationSource;
import com.harbor.boot.autoconfigure.redis.config.RedisRepositoryConfigurationExtension;
import com.harbor.boot.autoconfigure.redis.config.RepositoryConfigurationExtension;
import com.harbor.boot.autoconfigure.redis.config.RepositoryConfigurationUtils;
import com.harbor.boot.autoconfigure.redis.support.Streamable;
import com.harbor.boot.autoconfigure.redis.type.AnnotationMetadataUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
class RedisRepositoriesRegistrar implements ImportBeanDefinitionRegistrar,
        BeanFactoryAware, ResourceLoaderAware, EnvironmentAware {

    private static final Log log = LogFactory.getLog(RedisRepositoriesRegistrar.class);
    private ResourceLoader resourceLoader;
    private BeanFactory beanFactory;
    private Environment environment;


    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata metadata,
                                        @NonNull BeanDefinitionRegistry registry) {
        this.doRegisterBeanDefinitions(metadata, registry, null);
    }

    public void registerBeanDefinitions(@NonNull AnnotationMetadata metadata,
                                        @NonNull BeanDefinitionRegistry registry,
                                        @NonNull BeanNameGenerator generator) {
        this.doRegisterBeanDefinitions(metadata, registry, generator);

    }

    private void doRegisterBeanDefinitions(@NonNull AnnotationMetadata metadata,
                                           @NonNull BeanDefinitionRegistry registry,
                                           BeanNameGenerator generator) {
        AnnotationMetadata metadata2 = AnnotationMetadataUtils.introspect(getConfiguration());

        // Guard against calls for sub-classes
        if (metadata2.getAnnotationAttributes(getAnnotation().getName()) == null) {
            return;
        }

        RedisRepositoryConfigurationSource configurationSource = new RedisRepositoryConfigurationSource(metadata2,
                getAnnotation(), resourceLoader, environment, registry, generator);

        RepositoryConfigurationExtension extension = getExtension();
        RepositoryConfigurationUtils.exposeRegistration(extension, registry, configurationSource);

        RedisRepositoryConfigurationDelegate delegate = new RedisRepositoryConfigurationDelegate(configurationSource, resourceLoader,
                environment);

        List<BeanComponentDefinition> definitions = delegate.registerRepositoriesIn(registry, extension);
        for (BeanComponentDefinition definition : definitions) {
            log.info("加载redis repository bean " + definition.getBeanName());
        }
    }


    private RepositoryConfigurationExtension getExtension() {

        return new RedisRepositoryConfigurationExtension();
    }

    protected Streamable<String> getBasePackages() {
        return Streamable.of(AutoConfigurationPackages.get(this.beanFactory));
    }

    @EnableRedisRepositories
    private static class EnableRedisRepositoriesConfiguration {

    }

    protected Class<?> getConfiguration() {
        return RedisRepositoriesRegistrar.EnableRedisRepositoriesConfiguration.class;
    }


    protected Class<? extends Annotation> getAnnotation() {
        return EnableRedisRepositories.class;
    }

    @Override
    public void setResourceLoader(@NonNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    class RedisRepositoryConfigurationSource extends AnnotationRepositoryConfigurationSource {

        private final Environment environment;
        private final BeanDefinitionRegistry registry;

        public RedisRepositoryConfigurationSource(AnnotationMetadata metadata,
                                                  Class<? extends Annotation> annotation,
                                                  ResourceLoader resourceLoader,
                                                  Environment environment,
                                                  BeanDefinitionRegistry registry,
                                                  BeanNameGenerator generator) {
            super(metadata, annotation, resourceLoader, environment, registry, generator);
            this.environment = environment;
            this.registry = registry;
        }


        @Override
        public @NonNull Streamable<BeanDefinition> getCandidates(@NonNull ResourceLoader loader) {
            RepositoryScanningComponentProvider scanner = new RepositoryScanningComponentProvider(getIncludeFilters(), registry);
            scanner.setConsiderNestedRepositoryInterfaces(shouldConsiderNestedRepositories());
            scanner.setEnvironment(this.environment);
            scanner.setResourceLoader(loader);

            getExcludeFilters().forEach(scanner::addExcludeFilter);

            return Streamable.of(() -> getBasePackages().stream()//
                    .flatMap(it -> scanner.findCandidateComponents(it).stream()));
        }

        @NonNull
        @Override
        public Streamable<String> getBasePackages() {
            return RedisRepositoriesRegistrar.this.getBasePackages();
        }
    }
}