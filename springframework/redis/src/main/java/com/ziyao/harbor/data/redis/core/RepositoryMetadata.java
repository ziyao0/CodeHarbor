package com.ziyao.harbor.data.redis.core;

import org.springframework.data.util.TypeInformation;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface RepositoryMetadata {

    /**
     * Returns the repository interface.
     */
    Class<?> getRepositoryInterface();

    /**
     * Returns the {@link TypeInformation} of the id type of the repository.
     *
     * @return the {@link TypeInformation} class of the identifier of the entity managed by the repository. Will never be
     * {@literal null}.
     */
    TypeInformation<?> getIdTypeInformation();

    /**
     * Returns the {@link TypeInformation}of the domain type the repository is declared to manage. Will never be
     * {@literal null}.
     *
     * @return the domain class the repository is handling.
     */
    TypeInformation<?> getJavaTypeInformation();

    default Class<?> getIdType() {
        return getIdTypeInformation().getType();
    }

    default Class<?> getJavaType() {
        return getJavaTypeInformation().getType();
    }

}
