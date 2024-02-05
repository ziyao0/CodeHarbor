package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.data.redis.core.RedisRepositoryMetadata;
import com.ziyao.harbor.data.redis.core.Repository;
import com.ziyao.harbor.data.redis.core.RedisRepositoryFactoryInformation;
import com.ziyao.harbor.core.utils.Assert;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.util.Lazy;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
@Getter
public class RedisRepositoryFactoryBean<T extends Repository<K, V, HK, HV>, K, V, HK, HV> implements RedisRepositoryFactoryInformation,
        InitializingBean, FactoryBean<T>, BeanClassLoaderAware, BeanFactoryAware {
    private final Class<? extends T> repositoryInterface;
    private RedisRepositoryFactory factory;
    private Lazy<T> repository;
    private ClassLoader classLoader;
    private BeanFactory beanFactory;
    private final Optional<Class<?>> repositoryBaseClass = Optional.empty();
    private RedisRepositoryMetadata redisRepositoryMetadata;
    private RedisOperations<?, ?> operations;
    @Setter
    private boolean lazyInit = false;

    public RedisRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    public void setRedisOperations(RedisOperations<?, ?> operations) {

        Assert.notNull(operations, "KeyValueOperations must not be null");

        this.operations = operations;
    }

    @Override
    public void setBeanClassLoader(@NonNull ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public T getObject() {
        return this.repository.get();
    }

    @Override
    public Class<?> getObjectType() {
        return repositoryInterface;
    }

    @Override
    public void afterPropertiesSet() {
        this.factory = createRepositoryFactory();
        this.factory.setBeanClassLoader(classLoader);
        this.factory.setBeanFactory(beanFactory);
        repositoryBaseClass.ifPresent(this.factory::setRepositoryBaseClass);


        this.redisRepositoryMetadata = this.factory.getRepositoryMetadata(repositoryInterface);

        this.repository = Lazy.of(() -> this.factory.getRepository(repositoryInterface));

        // Make sure the aggregate root type is present in the MappingContext (e.g. for auditing)
//        this.mappingContext.ifPresent(it -> it.getPersistentEntity(cacheRepositoryMetadata.getValueType()));

        if (!lazyInit) {
            this.repository.get();
        }
    }

    /**
     * Create the actual {@link RedisRepositoryFactory} instance.
     */
    public RedisRepositoryFactory createRepositoryFactory() {
        Assert.notNull(operations, "operations are not initialized");

        return new RedisRepositoryFactory(operations);
    }
}
