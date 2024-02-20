package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.data.redis.core.Repository;
import com.ziyao.harbor.data.redis.core.RepositoryMetadata;
import com.ziyao.harbor.data.redis.repository.HashRepository;
import com.ziyao.harbor.data.redis.repository.KeyValueRepository;
import lombok.Getter;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
@Getter
public class DefaultRepositoryMetadata implements RepositoryMetadata {

    private static final String MUST_BE_A_REPOSITORY = String.format("Given type must be assignable to %s",
            Repository.class);
    private final TypeInformation<?> typeInformation;
    private final Class<?> repositoryInterface;
    private TypeInformation<?> keyTypeInformation;
    private TypeInformation<?> valueTypeInformation;
    private TypeInformation<?> hashKeyTypeInformation;
    private TypeInformation<?> hashValueTypeInformation;

    /**
     * Creates a new {@link DefaultRepositoryMetadata} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public DefaultRepositoryMetadata(Class<?> repositoryInterface) {

        Assert.notNull(repositoryInterface, "Given type must not be null");
        Assert.isTrue(repositoryInterface.isInterface(), "Given type must be an interface");

        this.repositoryInterface = repositoryInterface;
        this.typeInformation = TypeInformation.of(repositoryInterface);
        Assert.isTrue(Repository.class.isAssignableFrom(repositoryInterface), MUST_BE_A_REPOSITORY);

        List<TypeInformation<?>> keyvalueArguments = TypeInformation.of(repositoryInterface)//
                .getRequiredSuperTypeInformation(KeyValueRepository.class)//
                .getTypeArguments();
        List<TypeInformation<?>> hashArguments = TypeInformation.of(repositoryInterface)//
                .getRequiredSuperTypeInformation(HashRepository.class)//
                .getTypeArguments();

        if (Collections.nonNull(keyvalueArguments)) {
            this.keyTypeInformation = resolveTypeParameter(keyvalueArguments, 0,
                    () -> String.format("Could not resolve domain type of %s", repositoryInterface));

            this.valueTypeInformation = resolveTypeParameter(keyvalueArguments, 1,
                    () -> String.format("Could not resolve id type of %s", repositoryInterface));
        }
        if (Collections.nonNull(hashArguments)) {
            this.keyTypeInformation = resolveTypeParameter(hashArguments, 0,
                    () -> String.format("Could not resolve domain type of %s", repositoryInterface));

            this.hashKeyTypeInformation = resolveTypeParameter(hashArguments, 1,
                    () -> String.format("Could not resolve id type of %s", repositoryInterface));
            this.hashValueTypeInformation = resolveTypeParameter(hashArguments, 2,
                    () -> String.format("Could not resolve id type of %s", repositoryInterface));
        }


    }

    /**
     * Creates a new {@link com.ziyao.harbor.data.redis.core.RepositoryMetadata} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public static RepositoryMetadata getMetadata(Class<?> repositoryInterface) {

        Assert.notNull(repositoryInterface, "Repository interface must not be null");

        return Repository.class.isAssignableFrom(repositoryInterface) ? new DefaultRepositoryMetadata(repositoryInterface)
                : /*new AnnotationRepositoryMetadata(repositoryInterface)*/ null;
    }

    @Override
    public TypeInformation<?> getKeyTypeInformation() {
        return this.keyTypeInformation;
    }

    @Override
    public TypeInformation<?> getValueTypeInformation() {
        return this.valueTypeInformation;
    }

    @Override
    public TypeInformation<?> getHashKeyTypeInformation() {
        return this.hashKeyTypeInformation;
    }

    @Override
    public TypeInformation<?> getHashValueTypeInformation() {
        return this.hashValueTypeInformation;
    }

    @Override
    public Class<?> getRepositoryInterface() {
        return this.repositoryInterface;
    }

    private static TypeInformation<?> resolveTypeParameter(List<TypeInformation<?>> arguments, int index,
                                                           Supplier<String> exceptionMessage) {

        if ((arguments.size() <= index) || (arguments.get(index) == null)) {
            throw new IllegalArgumentException(exceptionMessage.get());
        }

        return arguments.get(index);
    }
}
