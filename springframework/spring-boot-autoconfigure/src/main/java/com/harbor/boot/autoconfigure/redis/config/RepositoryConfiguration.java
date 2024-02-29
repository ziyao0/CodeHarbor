package com.harbor.boot.autoconfigure.redis.config;

import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.util.Streamable;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
public interface RepositoryConfiguration<T extends RepositoryConfigurationSource> {

    /**
     * Returns the base packages that the repository was scanned under.
     */
    Streamable<String> getBasePackages();

    /**
     * Returns the base packages to scan for repository implementations.
     */
    Streamable<String> getImplementationBasePackages();

    /**
     * Returns the interface name of the repository.
     */
    String getRepositoryInterface();

    /**
     * Returns the key to resolve a {@link QueryLookupStrategy} from eventually.
     *
     * @see QueryLookupStrategy.Key
     */
    Object getQueryLookupStrategyKey();

    /**
     * Returns the location of the file containing Spring Data named queries.
     */
    Optional<String> getNamedQueriesLocation();

    /**
     * Returns the name of the repository base class to be used or {@literal null} if the store specific defaults shall be
     * applied.
     */
    Optional<String> getRepositoryBaseClassName();

    /**
     * Returns the name of the repository factory bean class to be used.
     */
    String getRepositoryFactoryBeanClassName();

    /**
     * Returns the custom implementation bean name to be used.
     */
    String getImplementationBeanName();

    /**
     * Returns the bean name of the repository to be used.
     */
    String getRepositoryBeanName();

    /**
     * Returns the source of the {@link RepositoryConfiguration}.
     */
    @Nullable
    Object getSource();

    /**
     * Returns the {@link RepositoryConfigurationSource} that backs the {@link RepositoryConfiguration}.
     */
    T getConfigurationSource();

    /**
     * Returns whether to initialize the repository proxy lazily.
     */
    boolean isLazyInit();

    /**
     * Returns whether the repository is the primary one for its type.
     */
    boolean isPrimary();

    /**
     * Returns the {@link TypeFilter}s to be used to exclude packages from repository scanning.
     */
    Streamable<TypeFilter> getExcludeFilters();

    /**
     * Returns the {@link ImplementationDetectionConfiguration} to be used for this repository.
     *
     * @param factory must not be {@literal null}.
     * @return will never be {@literal null}.
     */
    ImplementationDetectionConfiguration toImplementationDetectionConfiguration(MetadataReaderFactory factory);

    ImplementationLookupConfiguration toLookupConfiguration(MetadataReaderFactory factory);

    /**
     * Returns a human readable description of the repository interface declaration for error reporting purposes.
     *
     * @return can be {@literal null}.
     */
    @Nullable
    String getResourceDescription();
}
