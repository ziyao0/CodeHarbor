package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.data.redis.core.convert.RedisEntity;
import com.ziyao.harbor.data.redis.core.convert.RedisEntityConverter;
import com.ziyao.harbor.data.redis.core.convert.RedisUpdate;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.core.mapping.RedisPersistentEntity;
import org.springframework.data.redis.core.mapping.RedisPersistentProperty;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @time 2024/6/29
 */
public class RedisAdapter {

    private final RedisOperations<byte[], byte[]> redisOps;
    private final RedisEntityConverter converter;

    public RedisAdapter(RedisOperations<byte[], byte[]> redisOps) {
        this(redisOps, new RedisEntityConverter(new RedisMappingContext()));
    }

    public RedisAdapter(RedisOperations<byte[], byte[]> redisOps, RedisEntityConverter converter) {
        this.redisOps = redisOps;
        this.converter = converter;
    }

    @SuppressWarnings("deprecation")
    public <T> T findById(Object id, String keyspace, Class<T> type) {
        RedisPersistentEntity<?> entity = this.converter.getMappingContext()
                .getRequiredPersistentEntity(type);
        String keySpace = entity.getKeySpace();
        byte[] redisKey = createKey(keySpace, this.converter.getConversionService().convert(id, String.class));

        byte[] raw = redisOps.execute((RedisCallback<byte[]>) connection -> connection.get(redisKey));

        RedisEntity rdo = RedisEntity.createRedisEntity(raw);

        rdo.setId(keySpace);
        T target = this.converter.read(type, rdo);

        if (entity.hasExplictTimeToLiveProperty()) {

            RedisPersistentProperty ttlProperty = entity.getExplicitTimeToLiveProperty();

            if (ttlProperty == null) {
                return target;
            }

            TimeToLive ttl = ttlProperty.findAnnotation(TimeToLive.class);

            Long timeout = redisOps.execute((RedisCallback<Long>) connection -> {

                if (ObjectUtils.nullSafeEquals(TimeUnit.SECONDS, ttl != null ? ttl.unit() : null)) {
                    return connection.ttl(redisKey);
                }

                return connection.pTtl(redisKey, ttl != null ? ttl.unit() : TimeUnit.SECONDS);
            });

            if (timeout != null || !ttlProperty.getType().isPrimitive()) {

                PersistentPropertyAccessor<T> propertyAccessor = entity.getPropertyAccessor(target);

                propertyAccessor.setProperty(ttlProperty,
                        converter.getConversionService().convert(timeout, ttlProperty.getType()));

                target = propertyAccessor.getBean();
            }
        }

        return target;
    }

    public <T> void delete(Object id, String keyspace, Class<T> type) {

    }

    @SuppressWarnings("deprecation")
    public void update(RedisUpdate<?> update) {
        RedisPersistentEntity<?> entity = this.converter.getMappingContext()
                .getRequiredPersistentEntity(update.getTarget());

        String keySpace = entity.getKeySpace();
        byte[] redisKey = createKey(keySpace, this.converter.getConversionService().convert(update.getId(), String.class));

        RedisEntity rdo = new RedisEntity();
        this.converter.write(update, rdo);

        // @formatter:off
        redisOps.execute((RedisCallback<Void>) connection -> {
            connection.set(redisKey, rdo.getRaw());

            if (update.isRefresh()) {

                if (rdo.getTimeToLive().isPresent()) {
                    connection.expire(redisKey, rdo.getTimeToLive().get());
                }
            }
            else {
                connection.persist(redisKey);
            }
            return null;
        });
        // @formatter:on
    }

    private byte[] createKey(String keySpace, String id) {
        return Strings.toBytes(keySpace + Strings.COLON + id);
    }

}
