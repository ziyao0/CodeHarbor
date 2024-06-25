package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.support.serializer.DefaultSerializerInformationCreator;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformationCreator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Optional;

/**
 * @author ziyao
 * @since 2024/06/25 09:30:32
 */
public class DefaultRedisHashRepository<HK, HV> extends AbstractRepository<Object> implements RedisHashRepository<HK, HV> {

    private static final Log log = LogFactory.getLog(DefaultRedisHashRepository.class);

    private static final SerializerInformationCreator SerializerCreator = new DefaultSerializerInformationCreator();
    private final RedisOperations<String, Object> operations;

    public DefaultRedisHashRepository(RepositoryInformation repositoryInformation, RedisTemplate<String, Object> template) {
        super(template, repositoryInformation);
        // 设置序列化
        setSerializer(repositoryInformation, template);
        this.operations = template;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Map<HK, HV>> findById(String id) {
        return Optional.of(this.operations.opsForHash().entries(id)).map(v -> (Map<HK, HV>) v);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<HV> findByHashKey(String id, Object hashKey) {
        return Optional.ofNullable(this.operations.opsForHash().get(id, hashKey)).map(value -> (HV) value);
    }

    @Override
    public Long deleteByHashKey(String id, Object... hashKey) {
        return this.operations.opsForHash().delete(id, hashKey);
    }

    @Override
    public void save(String id, HK hashKey, HV hashValue) {
        this.operations.opsForHash().put(id, hashKey, hashValue);
    }

    @Override
    public void saveAll(String id, Map<HK, HV> map) {
        this.operations.opsForHash().putAll(id, map);
    }

    /**
     * 设置redis序列化
     */
    private void setSerializer(RepositoryInformation repositoryInformation, RedisTemplate<String, Object> template) {
        //创建redis序列化
        SerializerInformation<?, ?, ?, ?> metadata = SerializerCreator.getInformation(repositoryInformation);
        if (log.isDebugEnabled()) {
            log.debug("repositoryInterfaceClass [" + repositoryInformation.getRepositoryInterface().getName() + "],metadata:" + metadata);
        }
        template.setKeySerializer(metadata.getKeySerializer());
        template.setHashKeySerializer(metadata.getHashKeySerializer());
        template.setHashValueSerializer(metadata.getHashValueSerializer());
    }
}
