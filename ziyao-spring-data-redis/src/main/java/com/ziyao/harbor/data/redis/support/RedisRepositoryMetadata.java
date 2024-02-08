package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.data.redis.core.Repository;
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
public class RedisRepositoryMetadata implements com.ziyao.harbor.data.redis.core.RedisRepositoryMetadata {

    private static final String MUST_BE_A_REPOSITORY = String.format("Given type must be assignable to %s",
            Repository.class);
    private final TypeInformation<?> typeInformation;
    private final Class<?> repositoryInterface;
    private final TypeInformation<?> keyTypeInformation;
    private final TypeInformation<?> valueTypeInformation;
    private final TypeInformation<?> hashKeyTypeInformation;
    private final TypeInformation<?> hashValueTypeInformation;

    /**
     * Creates a new {@link RedisRepositoryMetadata} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public RedisRepositoryMetadata(Class<?> repositoryInterface) {

        Assert.notNull(repositoryInterface, "Given type must not be null");
        Assert.isTrue(repositoryInterface.isInterface(), "Given type must be an interface");

        this.repositoryInterface = repositoryInterface;
        this.typeInformation = TypeInformation.of(repositoryInterface);
        Assert.isTrue(Repository.class.isAssignableFrom(repositoryInterface), MUST_BE_A_REPOSITORY);

        List<TypeInformation<?>> arguments = TypeInformation.of(repositoryInterface)//
                .getRequiredSuperTypeInformation(Repository.class)//
                .getTypeArguments();

        this.keyTypeInformation = resolveTypeParameter(arguments, 0,
                () -> String.format("Could not resolve domain type of %s", repositoryInterface));

        this.valueTypeInformation = resolveTypeParameter(arguments, 1,
                () -> String.format("Could not resolve id type of %s", repositoryInterface));
        this.hashKeyTypeInformation = resolveTypeParameter(arguments, 2,
                () -> String.format("Could not resolve id type of %s", repositoryInterface));
        this.hashValueTypeInformation = resolveTypeParameter(arguments, 3,
                () -> String.format("Could not resolve id type of %s", repositoryInterface));
    }

    /**
     * Creates a new {@link com.ziyao.harbor.data.redis.core.RedisRepositoryMetadata} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public static com.ziyao.harbor.data.redis.core.RedisRepositoryMetadata getMetadata(Class<?> repositoryInterface) {

        Assert.notNull(repositoryInterface, "Repository interface must not be null");

        return Repository.class.isAssignableFrom(repositoryInterface) ? new RedisRepositoryMetadata(repositoryInterface)
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
