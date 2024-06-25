package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.support.serializer.DefaultSerializerInformationCreator;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformationCreator;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/25 15:36:54
 */
public class DefaultRedisSetRepository<T> extends AbstractRepository<T> implements RedisSetRepository<T> {


    private static final SerializerInformationCreator CREATOR = new DefaultSerializerInformationCreator();
    private final RedisOperations<String, T> operations;

    public DefaultRedisSetRepository(RepositoryInformation repositoryInformation, RedisTemplate<String, T> template) {
        super(template, repositoryInformation);
        // 设置序列化
        SerializerInformation<?, ?, ?, ?> serializerInformation = CREATOR.getInformation(repositoryInformation);
        template.setKeySerializer(serializerInformation.getKeySerializer());
        template.setValueSerializer(serializerInformation.getValueSerializer());
        this.operations = template;
    }


    @Override
    public Optional<Set<T>> findById(String id) {
        return Optional.ofNullable(this.operations.opsForSet().members(id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void save(String id, T value) {
        this.operations.opsForSet().add(id, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveAll(String id, T... values) {
        this.operations.opsForSet().add(id, values);
    }
}
