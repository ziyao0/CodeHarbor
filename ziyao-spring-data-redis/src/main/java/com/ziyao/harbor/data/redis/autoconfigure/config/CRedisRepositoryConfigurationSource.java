package com.ziyao.harbor.data.redis.autoconfigure.config;

import com.ziyao.harbor.data.redis.autoconfigure.RepositoryScanningComponentProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.util.Streamable;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;

/**
 * @author ziyao zhang
 * @since 2024/2/5
 */
public class CRedisRepositoryConfigurationSource extends AnnotationRepositoryConfigurationSource {

    private final Environment environment;
    private final BeanDefinitionRegistry registry;

    public CRedisRepositoryConfigurationSource(AnnotationMetadata metadata,
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
}
