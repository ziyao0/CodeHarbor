package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.support.serializer.DefaultSerializerInformationCreator;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformationCreator;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Optional;

/**
 * @author ziyao
 * @since 2024/06/25 15:17:24
 */
public class DefaultRedisListRepository<T> extends AbstractRepository<T> implements RedisListRepository<T> {

    private static final SerializerInformationCreator CREATOR = new DefaultSerializerInformationCreator();
    private final RedisOperations<String, T> operations;


    public DefaultRedisListRepository(RepositoryInformation repositoryInformation, RedisTemplate<String, T> template) {
        super(template, repositoryInformation);
        // 设置序列化
        SerializerInformation<?, ?, ?, ?> serializerInformation = CREATOR.getInformation(repositoryInformation);
        template.setKeySerializer(serializerInformation.getKeySerializer());
        template.setValueSerializer(serializerInformation.getValueSerializer());
        this.operations = template;
    }


    @Override
    public Optional<List<T>> findById(String id) {
        return Optional.ofNullable(this.operations.opsForList().range(id, 0, -1));
    }

    @Override
    public void save(String id, T value) {
        this.operations.opsForList().leftPush(id, value);
    }

    @Override
    public void saveAll(String id, List<T> values) {
        this.operations.opsForList().leftPushAll(id, values);
    }

    @Override
    public Optional<T> leftPop(String id) {
        return Optional.ofNullable(this.operations.opsForList().leftPop(id));
    }

    @Override
    public Optional<T> rightPop(String id) {
        return Optional.ofNullable(this.operations.opsForList().rightPop(id));
    }

    @Override
    public void leftPush(String id, T value) {
        this.operations.opsForList().leftPush(id, value);
    }

    @Override
    public void rightPush(String id, T value) {
        this.operations.opsForList().rightPush(id, value);
    }


}
