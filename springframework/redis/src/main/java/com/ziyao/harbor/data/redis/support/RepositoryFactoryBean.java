package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.data.redis.core.Repository;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.lang.NonNull;

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
    private DefaultRepositoryFactory factory;
    private final Class<? extends T> repositoryInterface;
    private T repository;

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
        return this.repository;
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
        this.repository = this.factory.getRepository(repositoryInterface);
    }

    private DefaultRepositoryFactory createRepositoryFactory() {
        Assert.notNull(operations, "operations are not initialized");
        return new DefaultRepositoryFactory(operations);
    }
}
