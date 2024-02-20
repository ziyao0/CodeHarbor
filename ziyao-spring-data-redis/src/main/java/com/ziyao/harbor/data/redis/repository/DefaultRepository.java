package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.Key;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.serializer.SerializerInformation;
import com.ziyao.harbor.data.redis.support.DefaultSerializerInformationCreator;
import org.springframework.data.redis.core.*;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public class DefaultRepository<K, V, HK, HV> implements KeyValueRepository<K, V>, HashRepository<K, HK, HV> {

    private final RedisTemplate<K, V> operations;
    private final String index;

    public DefaultRepository(RepositoryInformation repositoryInformation, RedisTemplate<K, V> operations) {
        this.operations = operations;
        DefaultSerializerInformationCreator creator = new DefaultSerializerInformationCreator();
        SerializerInformation<?, ?, ?, ?> metadata = creator.getInformation(
                repositoryInformation.getKeyType(),
                repositoryInformation.getValueType(),
                repositoryInformation.getHashKeyType(),
                repositoryInformation.getHashValueType());
        // 设置序列化
        operations.setKeySerializer(metadata.getKeySerializer());
        operations.setValueSerializer(metadata.getValueSerializer());
        operations.setHashKeySerializer(metadata.getHashKeySerializer());
        operations.setHashValueSerializer(metadata.getHashValueSerializer());
        Class<?> repositoryInterfaceClass = repositoryInformation.getRepositoryInterface();
        Key key = repositoryInterfaceClass.getAnnotation(Key.class);
        //该存储库的索引，可以理解为redis的key
        this.index = key.index();
    }


    @Override
    public HashOperations<K, HK, HV> opsForHash() {
        return operations.opsForHash();
    }

    @Override
    public ListOperations<K, V> opsForList() {
        return operations.opsForList();
    }

    @Override
    public SetOperations<K, V> opsForSet() {
        return operations.opsForSet();
    }

    @Override
    public ZSetOperations<K, V> opsForZSet() {
        return operations.opsForZSet();
    }

    @Override
    public ValueOperations<K, V> opsForValue() {
        return operations.opsForValue();
    }

    @Override
    public boolean hasKey() {
//        return Boolean.TRUE.equals(operations.hasKey());
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public String getKey() {
        return this.index;
    }
}
