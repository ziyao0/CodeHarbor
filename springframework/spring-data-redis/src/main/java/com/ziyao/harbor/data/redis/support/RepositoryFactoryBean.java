package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.data.redis.core.Repository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.util.Lazy;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Getter
public class RepositoryFactoryBean<T extends Repository>
        implements InitializingBean, FactoryBean<T>, BeanClassLoaderAware, BeanFactoryAware {

    private ClassLoader classLoader;
    private BeanFactory beanFactory;
    private RedisOperations<?, ?> operations;
    private final Optional<Class<?>> repositoryBaseClass = Optional.empty();
    private DefaultRepositoryFactory factory;
    private final Class<? extends T> repositoryInterface;
    private Lazy<T> repository;
    @Setter
    private boolean lazyInit = false;


    protected RepositoryFactoryBean(Class<? extends T> repositoryInterface) {
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
        return this.repositoryInterface;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.factory = createRepositoryFactory();
        this.factory.setBeanClassLoader(classLoader);
        this.factory.setBeanFactory(beanFactory);
        repositoryBaseClass.ifPresent(this.factory::setRepositoryBaseClass);
        this.repository = Lazy.of(() -> this.factory.getRepository(repositoryInterface));

        if (!this.lazyInit) {
            this.repository.get();
        }
    }

    private DefaultRepositoryFactory createRepositoryFactory() {
        Assert.notNull(operations, "operations are not initialized");
        return doCreateRepositoryFactory();
    }

    protected DefaultRepositoryFactory doCreateRepositoryFactory() {
        return new DefaultRepositoryFactory(operations);
    }
}
