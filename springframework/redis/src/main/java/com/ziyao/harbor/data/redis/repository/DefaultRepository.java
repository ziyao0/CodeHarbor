package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.data.redis.core.Expired;
import com.ziyao.harbor.data.redis.core.RedisKey;
import com.ziyao.harbor.data.redis.core.RepositoryInformation;
import com.ziyao.harbor.data.redis.support.RedisKeyFormatter;
import com.ziyao.harbor.data.redis.support.TimeoutUtils;
import com.ziyao.harbor.data.redis.support.serializer.DefaultSerializerInformationCreator;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformation;
import com.ziyao.harbor.data.redis.support.serializer.SerializerInformationCreator;
import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/2/2
 */
public class DefaultRepository<T> implements KeyValueRepository<T> {

    private static final Log log = LogFactory.getLog(DefaultRepository.class);
    private static final SerializerInformationCreator created = new DefaultSerializerInformationCreator();
    private final SerializerInformation metadata;
    private final RedisTemplate<String, T> operations;
    private final String key;
    private final String keyFormat;
    @Getter
    private final long timeout;

    public DefaultRepository(RepositoryInformation repositoryInformation, RedisTemplate<String, T> operations) {
        // 设置序列化
        this.operations = operations;
        Class<?> repositoryInterfaceClass = repositoryInformation.getRepositoryInterface();
        //解析redis key
        RedisKey redisKey = repositoryInterfaceClass.getAnnotation(RedisKey.class);
        this.key = redisKey.key();
        this.keyFormat = redisKey.format();
        // 解析过期时间
        this.timeout = extractTimeout(repositoryInterfaceClass);
        //初始化redis操作相关
        this.metadata = created.getInformation(repositoryInformation);
        if (log.isDebugEnabled()) {
            log.debug("repositoryInterfaceClass [" + repositoryInformation.getRepositoryInterface().getName() + "],metadata:" + metadata);
        }
        this.operations.setKeySerializer(metadata.getKeySerializer());
        this.operations.setValueSerializer(metadata.getValueSerializer());
        this.operations.setHashKeySerializer(metadata.getHashKeySerializer());
        this.operations.setHashValueSerializer(metadata.getHashValueSerializer());

    }

    private long extractTimeout(Class<?> repositoryInterfaceClass) {
        long timeout = -1;
        Expired expiration = repositoryInterfaceClass.getAnnotation(Expired.class);
        if (Objects.nonNull(expiration)) {
            timeout = TimeoutUtils.toSeconds(expiration.timeout(), expiration.unit());
        }
        return timeout > 0 ? timeout : -1;
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
    public boolean delete(String key) {
        return Boolean.TRUE.equals(operations.delete(key));
    }

    @Override
    public String getKey(String... arguments) {
        if (Strings.hasText(this.key))
            return key;
        else
            return format(arguments);
    }

    @Override
    public void refresh(String... arguments) {
        Assert.isTrue(this.timeout >= 0, "没有设置正确的过期时间，timeout:" + timeout);
        this.operations.expire(getKey(arguments), this.timeout, TimeUnit.SECONDS);
    }

    @Override
    public void refresh(long timeout, TimeUnit unit, String... arguments) {
        this.operations.expire(getKey(arguments), timeout, unit);
    }

    @Override
    public SerializerInformation getInformation() {
        return metadata;
    }

    private String format(String... arguments) {
        Assert.isTrue(RedisKeyFormatter.countOccurrencesOf(this.keyFormat) == arguments.length,
                "KEY中需要替换的字符串[" + this.keyFormat + "]和传入的数值[" + Arrays.toString(arguments) + "]长度不匹配，请核对传入的数据！");
        return RedisKeyFormatter.format(this.keyFormat, arguments);
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(operations.opsForValue().get(id));
    }

    @Override
    public void save(T value) {

    }

    @Override
    public void delete(T value) {

    }

    @Override
    public void deleteById(String id) {

    }
}
