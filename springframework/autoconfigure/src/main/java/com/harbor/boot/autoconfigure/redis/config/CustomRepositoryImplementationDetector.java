package com.harbor.boot.autoconfigure.redis.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.util.Lazy;
import org.springframework.data.util.StreamUtils;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
public class CustomRepositoryImplementationDetector {

    private static final String CUSTOM_IMPLEMENTATION_RESOURCE_PATTERN = "**/*%s.class";
    private static final String AMBIGUOUS_CUSTOM_IMPLEMENTATIONS = "Ambiguous custom implementations detected; Found %s but expected a single implementation";

    private final Environment environment;
    private final ResourceLoader resourceLoader;
    private final Lazy<Set<BeanDefinition>> implementationCandidates;

    /**
     * Creates a new {@link CustomRepositoryImplementationDetector} with the given {@link Environment},
     * {@link ResourceLoader} and {@link ImplementationDetectionConfiguration}. The latter will be registered for a
     * one-time component scan for implementation candidates that will the be used and filtered in all subsequent calls to
     * {@link #detectCustomImplementation(ImplementationLookupConfiguration)}.
     *
     * @param environment    must not be {@literal null}.
     * @param resourceLoader must not be {@literal null}.
     * @param configuration  must not be {@literal null}.
     */
    public CustomRepositoryImplementationDetector(Environment environment, ResourceLoader resourceLoader,
                                                  ImplementationDetectionConfiguration configuration) {

        Assert.notNull(environment, "Environment must not be null");
        Assert.notNull(resourceLoader, "ResourceLoader must not be null");
        Assert.notNull(configuration, "ImplementationDetectionConfiguration must not be null");

        this.environment = environment;
        this.resourceLoader = resourceLoader;
        this.implementationCandidates = Lazy.of(() -> findCandidateBeanDefinitions(configuration));
    }

    /**
     * Creates a new {@link org.springframework.data.repository.config.CustomRepositoryImplementationDetector} with the given {@link Environment} and
     * {@link ResourceLoader}. Calls to {@link #detectCustomImplementation(ImplementationLookupConfiguration)} will issue
     * scans for
     *
     * @param environment    must not be {@literal null}.
     * @param resourceLoader must not be {@literal null}.
     */
    public CustomRepositoryImplementationDetector(Environment environment, ResourceLoader resourceLoader) {

        Assert.notNull(environment, "Environment must not be null");
        Assert.notNull(resourceLoader, "ResourceLoader must not be null");

        this.environment = environment;
        this.resourceLoader = resourceLoader;
        this.implementationCandidates = Lazy.empty();
    }

    /**
     * Tries to detect a custom implementation for a repository bean by classpath scanning.
     *
     * @param lookup must not be {@literal null}.
     * @return the {@code AbstractBeanDefinition} of the custom implementation or {@literal null} if none found.
     */
    public Optional<AbstractBeanDefinition> detectCustomImplementation(ImplementationLookupConfiguration lookup) {

        Assert.notNull(lookup, "ImplementationLookupConfiguration must not be null");

        Set<BeanDefinition> definitions = implementationCandidates.getOptional()
                .orElseGet(() -> findCandidateBeanDefinitions(lookup)).stream() //
                .filter(lookup::matches) //
                .collect(StreamUtils.toUnmodifiableSet());

        return selectImplementationCandidate(lookup, definitions);
    }

    private static Optional<AbstractBeanDefinition> selectImplementationCandidate(
            ImplementationLookupConfiguration lookup, Set<BeanDefinition> definitions) {

        return SelectionSet //
                .of(definitions, c -> c.isEmpty() ? firstOrEmptyBeanDefinition(definitions) : throwAmbiguousCustomImplementationException(c)) //
                .filterIfNecessary(lookup::hasMatchingBeanName) //
                .uniqueResult() //
                .map(AbstractBeanDefinition.class::cast);
    }

    static Optional<BeanDefinition> firstOrEmptyBeanDefinition(Set<BeanDefinition> definitions) {
        return definitions.isEmpty() ? Optional.empty() : Optional.of(definitions.iterator().next());
    }

    private Set<BeanDefinition> findCandidateBeanDefinitions(ImplementationDetectionConfiguration config) {

        String postfix = config.getImplementationPostfix();

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false,
                environment);
        provider.setResourceLoader(resourceLoader);
        provider.setResourcePattern(String.format(CUSTOM_IMPLEMENTATION_RESOURCE_PATTERN, postfix));
        provider.setMetadataReaderFactory(config.getMetadataReaderFactory());
        provider.addIncludeFilter((reader, factory) -> true);

        config.getExcludeFilters().forEach(it -> provider.addExcludeFilter(it));

        return config.getBasePackages().stream()//
                .flatMap(it -> provider.findCandidateComponents(it).stream())//
                .collect(Collectors.toSet());
    }

    private static Optional<BeanDefinition> throwAmbiguousCustomImplementationException(
            Collection<BeanDefinition> definitions) {

        String implementationNames = definitions.stream()//
                .map(BeanDefinition::getBeanClassName)//
                .collect(Collectors.joining(", "));

        throw new IllegalStateException(String.format(AMBIGUOUS_CUSTOM_IMPLEMENTATIONS, implementationNames));
    }
}
