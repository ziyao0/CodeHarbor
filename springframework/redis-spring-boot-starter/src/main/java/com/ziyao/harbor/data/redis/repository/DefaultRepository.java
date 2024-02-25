package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.data.redis.core.*;
import com.ziyao.harbor.data.redis.support.RedisKeyFormatter;
import com.ziyao.harbor.data.redis.support.serializer.DefaultSerializerInformationCreator;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;

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
        RedisKey key = repositoryInterfaceClass.getAnnotation(RedisKey.class);
        //解析redis key
        this.key = key.value();
        //初始化redis操作相关
        this.hashOperations = new DefaultHashOperations<>(operations, this.key);
        this.valueOperations = new DefaultValueOperations<>(operations, this.key);
        this.listOperations = new DefaultListOperations<>(operations, this.key);
        this.setOperations = new DefaultSetOperations<>(operations, this.key);
        this.zSetOperations = new DefaultZSetOperations<>(operations, this.key);
    }


    @Override
    public HashOperations<HK, HV> opsForHash(String... arguments) {
        hashOperations.setKey(getKey(arguments));
        return hashOperations;
    }

    @Override
    public ListOperations<V> opsForList(String... arguments) {
        listOperations.setKey(getKey(arguments));
        return listOperations;
    }

    @Override
    public SetOperations<V> opsForSet(String... arguments) {
        setOperations.setKey(getKey(arguments));
        return setOperations;
    }

    @Override
    public ZSetOperations<V> opsForZSet(String... arguments) {
        zSetOperations.setKey(getKey(arguments));
        return zSetOperations;
    }

    @Override
    public ValueOperations<V> opsForValue(String... arguments) {
        valueOperations.setKey(getKey(arguments));
        return valueOperations;
    }

    @Override
    public boolean hasKey(String... arguments) {
        return Boolean.TRUE.equals(operations.hasKey(getKey(arguments)));
    }

    @Override
    public boolean delete(String... arguments) {
        return Boolean.TRUE.equals(operations.delete(getKey(arguments)));
    }

    @Override
    public String getKey(String... arguments) {
        return replace(arguments);
    }

    private String replace(String... arguments) {
        Assert.isTrue(RedisKeyFormatter.countOccurrencesOf(this.key) == arguments.length,
                "KEY中需要替换的字符串[" + this.key + "]和传入的数值[" + Arrays.toString(arguments) + "]长度不匹配，请核对传入的数据！");
        return RedisKeyFormatter.format(this.key, arguments);
    }
}
