package com.harbor.boot.autoconfigure.redis.config;

import com.harbor.boot.autoconfigure.redis.RepositoryScanningComponentProvider;
import com.harbor.boot.autoconfigure.redis.support.Streamable;
import lombok.Getter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;

import java.util.Collections;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
public abstract class RepositoryConfigurationSourceSupport implements RepositoryConfigurationSource {
    protected static final String DEFAULT_REPOSITORY_IMPL_POSTFIX = "Impl";

    private final Environment environment;
    private final RepositoryBeanNameGenerator beanNameGenerator;
    private final BeanDefinitionRegistry registry;

    /**
     * Creates a new {@link RepositoryConfigurationSourceSupport} with the given environment.
     *
     * @param environment must not be {@literal null}.
     * @param classLoader must not be {@literal null}.
     * @param registry    must not be {@literal null}.
     */
    public RepositoryConfigurationSourceSupport(Environment environment, ClassLoader classLoader,
                                                BeanDefinitionRegistry registry, BeanNameGenerator generator) {

        Assert.notNull(environment, "Environment must not be null");
        Assert.notNull(classLoader, "ClassLoader must not be null");
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null");

        this.environment = environment;
        this.beanNameGenerator = new RepositoryBeanNameGenerator(classLoader, generator, registry);
        this.registry = registry;
    }

    @Override
    public Streamable<BeanDefinition> getCandidates(ResourceLoader loader) {

        RepositoryScanningComponentProvider scanner = new RepositoryScanningComponentProvider(getIncludeFilters(), registry);
        scanner.setConsiderNestedRepositoryInterfaces(shouldConsiderNestedRepositories());
        scanner.setEnvironment(environment);
        scanner.setResourceLoader(loader);

        getExcludeFilters().forEach(scanner::addExcludeFilter);

        return Streamable.of(() -> getBasePackages().stream()//
                .flatMap(it -> scanner.findCandidateComponents(it).stream()));
    }

    /**
     * Return the {@link TypeFilter}s to define which types to exclude when scanning for repositories. Default
     * implementation returns an empty collection.
     *
     * @return must not be {@literal null}.
     */
    @Override
    public Streamable<TypeFilter> getExcludeFilters() {
        return Streamable.empty();
    }

    @Override
    public String generateBeanName(BeanDefinition beanDefinition) {
        return beanNameGenerator.generateBeanName(beanDefinition);
    }

    /**
     * Return the {@link TypeFilter}s to define which types to include when scanning for repositories. Default
     * implementation returns an empty collection.
     *
     * @return must not be {@literal null}.
     */
    protected Iterable<TypeFilter> getIncludeFilters() {
        return Collections.emptySet();
    }

    /**
     * Returns whether we should consider nested repositories, i.e. repository interface definitions nested in other
     * classes.
     *
     * @return {@literal true} if the container should look for nested repository interface definitions.
     */
    public boolean shouldConsiderNestedRepositories() {
        return false;
    }

    @Override
    public ImplementationDetectionConfiguration toImplementationDetectionConfiguration(MetadataReaderFactory factory) {
        return new SpringImplementationDetectionConfiguration(this, factory);
    }

    private static class SpringImplementationDetectionConfiguration implements ImplementationDetectionConfiguration {

        private final RepositoryConfigurationSource source;
        @Getter
        private final MetadataReaderFactory metadataReaderFactory;

        SpringImplementationDetectionConfiguration(RepositoryConfigurationSource source,
                                                   MetadataReaderFactory metadataReaderFactory) {
            this.source = source;
            this.metadataReaderFactory = metadataReaderFactory;
        }

        @Override
        public String getImplementationPostfix() {
            return source.getRepositoryImplementationPostfix()
                    .orElse(DEFAULT_REPOSITORY_IMPL_POSTFIX);
        }

        @Override
        public Streamable<String> getBasePackages() {
            return source.getBasePackages();
        }

        @Override
        public Streamable<TypeFilter> getExcludeFilters() {
            return source.getExcludeFilters();
        }

        @Override
        public String generateBeanName(BeanDefinition definition) {
            return source.generateBeanName(definition);
        }

    }
}
