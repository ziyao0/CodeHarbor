package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.*;
import com.ziyao.harbor.data.redis.support.serializer.DefaultSerializerInformationCreator;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public class DefaultRepository<V, HK, HV> implements KeyValueRepository<V>, HashRepository<HK, HV> {

    private final RedisTemplate<String, V> operations;
    private final String key;
    private final HashOperations<HK, HV> hashOperations;
    private final ValueOperations<V> valueOperations;
    private final ListOperations<V> listOperations;
    private final SetOperations<V> setOperations;
    private final ZSetOperations<V> zSetOperations;

    public DefaultRepository(RepositoryInformation repositoryInformation, RedisTemplate<String, V> operations) {
        this.operations = operations;
        DefaultSerializerInformationCreator creator = new DefaultSerializerInformationCreator();
        SerializerInformation<?, ?, ?, ?> metadata = creator.getInformation(repositoryInformation);
        // 设置序列化
        operations.setKeySerializer(metadata.getKeySerializer());
        operations.setValueSerializer(metadata.getValueSerializer());
        operations.setHashKeySerializer(metadata.getHashKeySerializer());
        operations.setHashValueSerializer(metadata.getHashValueSerializer());
        Class<?> repositoryInterfaceClass = repositoryInformation.getRepositoryInterface();
        RedisKey redisKey = repositoryInterfaceClass.getAnnotation(RedisKey.class);
        //解析redis key
        this.key = redisKey.value();
        //初始化redis操作相关
        this.hashOperations = new DefaultHashOperations<>(operations, this.key);
        this.valueOperations = new DefaultValueOperations<>(operations, this.key);
        this.listOperations = new DefaultListOperations<>(operations, this.key);
        this.setOperations = new DefaultSetOperations<>(operations, this.key);
        this.zSetOperations = new DefaultZSetOperations<>(operations, this.key);
    }


    @Override
    public HashOperations<HK, HV> opsForHash() {
        return hashOperations;
    }

    @Override
    public ListOperations<V> opsForList() {
        return listOperations;
    }

    @Override
    public SetOperations<V> opsForSet() {
        return setOperations;
    }

    @Override
    public ZSetOperations<V> opsForZSet() {
        return zSetOperations;
    }

    @Override
    public ValueOperations<V> opsForValue() {
        return valueOperations;
    }

    @Override
    public boolean hasKey() {
        return Boolean.TRUE.equals(operations.hasKey(this.key));
    }

    @Override
    public boolean delete() {
        return Boolean.TRUE.equals(operations.delete(key));
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
