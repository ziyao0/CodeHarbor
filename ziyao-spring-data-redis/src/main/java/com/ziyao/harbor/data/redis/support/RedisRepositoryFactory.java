package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.data.redis.core.RedisEntityInformation;
import com.ziyao.harbor.data.redis.core.RedisEntityInformationCreator;
import com.ziyao.harbor.data.redis.core.RedisRepositoryInformation;
import com.ziyao.harbor.data.redis.core.Repository;
import com.ziyao.harbor.data.redis.repository.DefaultRedisRepository;
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
import org.springframework.transaction.interceptor.TransactionalProxy;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ziyao zhang
 * @since 2024/2/4
 */
@Getter
public class RedisRepositoryFactory implements BeanClassLoaderAware, BeanFactoryAware {
    private ClassLoader classLoader;
    private BeanFactory beanFactory;
    private Optional<Class<?>> repositoryBaseClass;
    private final RedisEntityInformationCreator redisEntityInformationCreator;
    private final Map<String, RedisRepositoryInformation> repositoryInformationCache;
    private final RedisOperations<?, ?> redisOperations;

    public RedisRepositoryFactory(RedisOperations<?, ?> redisOperations) {
        this.redisOperations = redisOperations;
        this.repositoryInformationCache = new ConcurrentReferenceHashMap<>(16, ConcurrentReferenceHashMap.ReferenceType.WEAK);
        this.redisEntityInformationCreator = new DefaultCacheEntityInformationCreator();

        this.repositoryBaseClass = Optional.empty();
    }


    protected com.ziyao.harbor.data.redis.core.RedisRepositoryMetadata getRepositoryMetadata(Class<?> repositoryInterface) {
        return new RedisRepositoryMetadata(repositoryInterface);
    }

    /**
     * Returns a repository instance for the given interface backed by an instance providing implementation logic for
     * custom logic.
     */
    @SuppressWarnings({"unchecked"})
    public <T> T getRepository(Class<T> repositoryInterface) {

        com.ziyao.harbor.data.redis.core.RedisRepositoryMetadata metadata = getRepositoryMetadata(repositoryInterface);
        RedisRepositoryInformation information = getRepositoryInformation(metadata);
        Object target = getTargetRepository(information);

        ProxyFactory result = new ProxyFactory();
        result.setTarget(target);
        result.setInterfaces(repositoryInterface, Repository.class, TransactionalProxy.class);
        return (T) result.getProxy(classLoader);
    }

    private RedisRepositoryInformation getRepositoryInformation(com.ziyao.harbor.data.redis.core.RedisRepositoryMetadata metadata) {

        String cacheKey = metadata.getRepositoryInterface().getSimpleName();

        return repositoryInformationCache.computeIfAbsent(cacheKey, key -> {
            Class<?> baseClass = repositoryBaseClass.orElse(getRepositoryBaseClass(metadata));

            return new DefaultRedisRepositoryInformation(metadata, baseClass);
        });
    }

    protected Object getTargetRepository(RedisRepositoryInformation metadata) {
        return getTargetRepositoryViaReflection(metadata,
                getEntityInformation(metadata.getKeyType(),
                        metadata.getValueType(),
                        metadata.getHashKeyType(),
                        metadata.getHashValueType()), redisOperations);
    }

    /**
     * Returns the base class backing the actual repository instance. Make sure
     * {@link #getTargetRepository(RedisRepositoryInformation)} returns an instance of this class.
     */
    protected Class<?> getRepositoryBaseClass(com.ziyao.harbor.data.redis.core.RedisRepositoryMetadata metadata) {
        if (!isCacheRepository(metadata.getRepositoryInterface())) {
            throw new IllegalArgumentException("redis query Support has not been implemented yet.");
        }

        return DefaultRedisRepository.class;
    }

    protected final <R> R getTargetRepositoryViaReflection(RedisRepositoryInformation information,
                                                           Object... constructorArguments) {

        Class<?> baseClass = information.getRepositoryBaseClass();
        return instantiateClass(baseClass, constructorArguments);
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

    private boolean isCacheRepository(Class<?> repositoryInterface) {
        return Repository.class.isAssignableFrom(repositoryInterface);
    }

    public void setRepositoryBaseClass(Class<?> repositoryBaseClass) {
        this.repositoryBaseClass = Optional.ofNullable(repositoryBaseClass);
    }

    public <K, V, HK, HV> RedisEntityInformation<K, V, HK, HV>
    getEntityInformation(Class<K> keyClass,
                         Class<V> valueClass,
                         Class<HK> hkeyClass,
                         Class<HV> hvalueClass) {
        return redisEntityInformationCreator.getInformation(keyClass,
                valueClass, hkeyClass, hvalueClass);
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
