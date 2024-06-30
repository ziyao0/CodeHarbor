package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisEntityInformation;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.support.serializer.DefaultSerializerInformationCreator;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public class DefaultRedisValueRepository<T, ID> extends AbstractRepository<T, ID> implements RedisValueRepository<T, ID> {

    private static final Log log = LogFactory.getLog(DefaultRedisValueRepository.class);

    private final RedisOperations<ID, T> operations;


    public DefaultRedisValueRepository(RepositoryInformation repositoryInformation, RedisTemplate<ID, T> template) {
        super(template, repositoryInformation);
        // 设置序列化
        SerializerInformation<?, ?, ?, ?> metadata = new DefaultSerializerInformationCreator().getInformation(repositoryInformation);
        template.setKeySerializer(metadata.getKeySerializer());
        template.setValueSerializer(metadata.getValueSerializer());
        this.operations = template;
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(operations.opsForValue().get(id));
    }

    @Override
    public void save(T entity) {

        RedisEntityInformation<T, ID> entityInformation = getEntityInformation();
        ID id = entityInformation.getId(entity);

        if (null == id) {
            throw new IllegalArgumentException("未在实体类中获取存在id属性的字段或类");
        }
        if (entityInformation.hasExplicitTimeToLiveProperty()) {
            Optional<Long> timeToLiveOptional = entityInformation.getTimeToLive(entity);
            timeToLiveOptional.ifPresent(timeToLive -> this.operations.opsForValue().set(id, entity, timeToLive, TimeUnit.SECONDS));
        } else {
            this.operations.opsForValue().set(id, entity);
        }
    }

    @Override
    public boolean saveIfAbsent(T entity) {
        RedisEntityInformation<T, ID> entityInformation = getEntityInformation();

        Optional<String> keySpace = entityInformation.getKeySpace();

        ID id = entityInformation.getId(entity);

        if (null == id) {
            throw new IllegalArgumentException("未在实体类中获取存在id属性的字段或类");
        }


        if (entityInformation.hasExplicitTimeToLiveProperty()) {
            Optional<Long> timeToLiveOptional = entityInformation.getTimeToLive(entity);
            return timeToLiveOptional.map(
                            timeToLive -> Optional.ofNullable(this.operations.opsForValue()
                                    .setIfAbsent(id, entity, timeToLive, TimeUnit.SECONDS)).orElse(false))
                    .orElseGet(() -> Optional.ofNullable(this.operations.opsForValue().setIfAbsent(id, entity)).orElse(false));
        } else {
            return Optional.ofNullable(this.operations.opsForValue().setIfAbsent(id, entity)).orElse(false);
        }
    }
}
