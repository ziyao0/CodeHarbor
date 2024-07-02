package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.data.redis.core.RedisRepository;
import com.ziyao.harbor.data.redis.core.Repository;
import com.ziyao.harbor.data.redis.core.RepositoryMetadata;
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

    private TypeInformation<?> javaTypeInformation;
    private TypeInformation<?> idTypeInformation;

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

        // HashRepository
        TypeInformation<?> redisTypeInformation = TypeInformation.of(repositoryInterface)//
                .getSuperTypeInformation(RedisRepository.class);
        if (redisTypeInformation != null) {
            List<TypeInformation<?>> hashArguments = redisTypeInformation.getTypeArguments();
            if (Collections.nonNull(hashArguments)) {
                this.javaTypeInformation = resolveTypeParameter(hashArguments, 0,
                        () -> String.format("Could not resolve id type of %s", repositoryInterface));
                this.idTypeInformation = resolveTypeParameter(hashArguments, 1,
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


    public TypeInformation<?> getIdTypeInformation() {
        return Objects.requireNonNullElseGet(this.idTypeInformation, () -> TypeInformation.of(Object.class));
    }

    public TypeInformation<?> getJavaTypeInformation() {
        return Objects.requireNonNullElseGet(this.javaTypeInformation, () -> TypeInformation.of(Object.class));
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
