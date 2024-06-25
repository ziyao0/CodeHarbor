package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.Expired;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.support.TimeoutUtils;
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
public class DefaultKeyValueRepository<T> extends AbstractRepository<T> implements KeyValueRepository<T> {

    private static final Log log = LogFactory.getLog(DefaultKeyValueRepository.class);
    private static final SerializerInformationCreator SerializerInformationCreator = new DefaultSerializerInformationCreator();

    private final RedisOperations<String, T> operations;

    private final long timeout;

    public DefaultKeyValueRepository(RepositoryInformation repositoryInformation, RedisTemplate<String, T> template) {
        super(template);
        // 获取过期时间
        Expired expiration = repositoryInformation.getRepositoryInterface().getAnnotation(Expired.class);
        this.timeout = expiration != null && expiration.timeout() > 0
                ? TimeoutUtils.toSeconds(expiration.timeout(), expiration.unit()) : -1;
        // 设置序列化
        setSerializer(repositoryInformation, template);
        this.operations = template;
    }

    @Override
    public void expire(String key) {
        super.expire(key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(operations.opsForValue().get(id));
    }

    @Override
    public void save(String id, T value) {
        if (timeout > 0) {
            this.save(id, value, timeout, TimeUnit.SECONDS);
        } else {
            this.operations.opsForValue().set(id, value);
        }
    }

    @Override
    public void save(String id, T value, long timeout, TimeUnit timeUnit) {
        this.operations.opsForValue().set(id, value, timeout, timeUnit);
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
