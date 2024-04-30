package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.data.redis.core.Repository;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.core.RepositoryMetadata;
import com.ziyao.harbor.data.redis.repository.DefaultRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.lang.NonNull;
import org.springframework.transaction.interceptor.TransactionalProxy;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Getter
public class DefaultRepositoryFactory implements BeanClassLoaderAware, BeanFactoryAware {
    private ClassLoader classLoader;
    private BeanFactory beanFactory;
    @Setter
    private Class<?> repositoryBaseClass;
    private final RedisOperations<?, ?> redisOperations;

    public DefaultRepositoryFactory(RedisOperations<?, ?> redisOperations) {
        this.redisOperations = redisOperations;
        this.repositoryBaseClass = DefaultRepository.class;
    }

    /**
     * Returns a repository instance for the given interface backed by an instance providing implementation logic for
     * custom logic.
     */
    @SuppressWarnings({"unchecked"})
    public <T> T getRepository(Class<T> repositoryInterface) {

        RepositoryMetadata metadata = getRepositoryMetadata(repositoryInterface);
        RepositoryInformation information = getRepositoryInformation(metadata);
        Object target = getTargetRepository(information);

        ProxyFactory result = new ProxyFactory();
        result.setTarget(target);
        result.setInterfaces(repositoryInterface, Repository.class);
        return (T) result.getProxy(classLoader);
    }

    private RepositoryInformation getRepositoryInformation(RepositoryMetadata metadata) {

        Class<?> baseClass = getRepositoryBaseClass(metadata);

        return new DefaultRepositoryInformation(metadata, baseClass);
    }


    /**
     * Returns the base class backing the actual repository instance. Make sure
     * {@link #getTargetRepository(RepositoryInformation)} returns an instance of this class.
     */
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (!isCacheRepository(metadata.getRepositoryInterface())) {
            throw new IllegalArgumentException("redis query Support has not been implemented yet.");
        }
        return this.repositoryBaseClass;
    }


    protected Object getTargetRepository(RepositoryInformation metadata) {
        return getTargetRepositoryViaReflection(metadata, metadata, redisOperations);
    }

    protected final <R> R getTargetRepositoryViaReflection(RepositoryInformation information,
                                                           Object... constructorArguments) {

        Class<?> baseClass = information.getRepositoryBaseClass();
        return instantiateClass(baseClass, constructorArguments);
    }

    protected RepositoryMetadata getRepositoryMetadata(Class<?> repositoryInterface) {
        return new DefaultRepositoryMetadata(repositoryInterface);
    }

    @SuppressWarnings("unchecked")
    protected final <R> R instantiateClass(Class<?> baseClass, Object... constructorArguments) {

        Optional<Constructor<?>> constructor = ReflectionUtils.findConstructor(baseClass, constructorArguments);

        return constructor.map(it -> (R) BeanUtils.instantiateClass(it, constructorArguments))
                .orElseThrow(() -> new IllegalStateException(String.format(
                        "No suitable constructor found on %s to match the given arguments: %s. Make sure you implement a constructor taking these",
                        baseClass, Arrays.stream(constructorArguments).map(Object::getClass).map(ClassUtils::getQualifiedName)
                                .collect(Collectors.joining(", ")))));
    }

    //判断是否为缓存库
    protected boolean isCacheRepository(Class<?> repositoryInterface) {
        return Repository.class.isAssignableFrom(repositoryInterface);
    }

    @Override
    public void setBeanClassLoader(@NonNull ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
