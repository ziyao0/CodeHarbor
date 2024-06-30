package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.support.serializer.DefaultSerializerInformationCreator;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformationCreator;
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
@Deprecated
public class DefaultRedisStringKeyValueRepository<T> extends AbstractRepository<T, String> implements RedisStringKeyValueRepository<T> {

    private static final Log log = LogFactory.getLog(DefaultRedisStringKeyValueRepository.class);
    private static final SerializerInformationCreator SerializerInformationCreator = new DefaultSerializerInformationCreator();

    private final RedisOperations<String, T> operations;


    public DefaultRedisStringKeyValueRepository(RepositoryInformation repositoryInformation, RedisTemplate<String, T> template) {
        super(template, repositoryInformation);
        // 设置序列化
        setSerializer(repositoryInformation, template);
        this.operations = template;
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(operations.opsForValue().get(id));
    }

    @Override
    public void save(String id, T value) {
        this.operations.opsForValue().set(id, value);
    }

    @Override
    public void save(String id, T value, long timeout, TimeUnit timeUnit) {
        this.operations.opsForValue().set(id, value, timeout, timeUnit);
    }

    @Override
    public boolean saveIfAbsent(String id, T value) {
        return Optional.ofNullable(this.operations.opsForValue().setIfAbsent(id, value)).orElse(false);
    }

    /**
     * 设置redis序列化
     */
    private void setSerializer(RepositoryInformation repositoryInformation, RedisTemplate<String, T> template) {
        //创建redis序列化
        SerializerInformation<?, ?, ?, ?> metadata = SerializerInformationCreator.getInformation(repositoryInformation);
        if (log.isDebugEnabled()) {
            log.debug("repositoryInterfaceClass [" + repositoryInformation.getRepositoryInterface().getName() + "],metadata:" + metadata);
        }
        template.setKeySerializer(metadata.getKeySerializer());
        template.setValueSerializer(metadata.getValueSerializer());
    }
}
