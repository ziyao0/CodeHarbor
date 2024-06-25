package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.data.redis.core.Repository;
import com.ziyao.harbor.data.redis.core.RepositoryMetadata;
import com.ziyao.harbor.data.redis.repository.RedisHashRepository;
import com.ziyao.harbor.data.redis.repository.RedisValueRepository;
import lombok.Getter;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
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
    private final TypeInformation<?> keyTypeInformation;
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
        this.keyTypeInformation = TypeInformation.of(String.class);
        Assert.isTrue(Repository.class.isAssignableFrom(repositoryInterface), MUST_BE_A_REPOSITORY);

        // KeyValueRepository
        TypeInformation<?> keyValueTypeInformation = TypeInformation.of(repositoryInterface)//
                .getSuperTypeInformation(RedisValueRepository.class);
        if (keyValueTypeInformation != null) {
            List<TypeInformation<?>> keyvalueArguments = keyValueTypeInformation.getTypeArguments();
            if (Collections.nonNull(keyvalueArguments)) {
                this.valueTypeInformation = resolveTypeParameter(keyvalueArguments, 0,
                        () -> String.format("Could not resolve id type of %s", repositoryInterface));
            }
        }
        // HashRepository
        TypeInformation<?> hashTypeInformation = TypeInformation.of(repositoryInterface)//
                .getSuperTypeInformation(RedisHashRepository.class);
        if (hashTypeInformation != null) {
            List<TypeInformation<?>> hashArguments = hashTypeInformation.getTypeArguments();
            if (Collections.nonNull(hashArguments)) {

                this.hashKeyTypeInformation = resolveTypeParameter(hashArguments, 0,
                        () -> String.format("Could not resolve id type of %s", repositoryInterface));
                this.hashValueTypeInformation = resolveTypeParameter(hashArguments, 1,
                        () -> String.format("Could not resolve id type of %s", repositoryInterface));
            }
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
        return Objects.requireNonNullElseGet(this.keyTypeInformation, () -> TypeInformation.of(String.class));
    }

    @Override
    public TypeInformation<?> getValueTypeInformation() {
        return Objects.requireNonNullElseGet(this.valueTypeInformation, () -> TypeInformation.of(Object.class));
    }

    @Override
    public TypeInformation<?> getHashKeyTypeInformation() {
        return Objects.requireNonNullElseGet(this.hashKeyTypeInformation, () -> TypeInformation.of(Object.class));
    }

    @Override
    public TypeInformation<?> getHashValueTypeInformation() {
        return Objects.requireNonNullElseGet(this.hashValueTypeInformation, () -> TypeInformation.of(Object.class));
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
