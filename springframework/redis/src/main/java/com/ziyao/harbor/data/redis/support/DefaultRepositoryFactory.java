package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.data.redis.core.Repository;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.core.RepositoryMetadata;
import com.ziyao.harbor.data.redis.repository.*;
import lombok.Getter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.lang.NonNull;
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


    private final RedisOperations<byte[], byte[]> redisOps;

    public DefaultRepositoryFactory(RedisOperations<byte[], byte[]> redisOps) {
        this.redisOps = redisOps;
    }

    /**
     * 返回给定接口的存储库实例，该实例由为自定义逻辑提供实现逻辑的实例提供支持。
     */
    @SuppressWarnings({"unchecked"})
    public <T> T getRepository(Class<T> repositoryInterface) {

        RepositoryMetadata metadata = getRepositoryMetadata(repositoryInterface);
        RepositoryInformation information = getRepositoryInformation(metadata);
        // 创建redisRepository对象
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
     * 返回支持实际存储库实例的基类。确保{@link #getTargetRepository(RepositoryInformation)} 返回此类的实例。
     */
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (!isCacheRepository(metadata.getRepositoryInterface())) {
            throw new IllegalArgumentException("redis query Support has not been implemented yet.");
        }
        if (RedisHashRepository.class.isAssignableFrom(metadata.getRepositoryInterface())) {
            return DefaultRedisHashRepository.class;
        }
        if (RedisValueRepository.class.isAssignableFrom(metadata.getRepositoryInterface())) {
            return DefaultRedisValueRepository.class;
        }
        if (RedisListRepository.class.isAssignableFrom(metadata.getRepositoryInterface())) {
            return DefaultRedisListRepository.class;
        }
        if (RedisSetRepository.class.isAssignableFrom(metadata.getRepositoryInterface())) {
            return DefaultRedisSetRepository.class;
        }
        throw new IllegalArgumentException("redis query Support has not been implemented yet.");
    }


    protected Object getTargetRepository(RepositoryInformation metadata) {
        return getTargetRepositoryViaReflection(metadata, metadata, redisOps);
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
