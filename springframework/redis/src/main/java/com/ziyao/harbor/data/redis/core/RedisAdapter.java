package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.data.redis.core.convert.RedisEntityConverter;
import com.ziyao.harbor.data.redis.core.convert.RedisMetadata;
import com.ziyao.harbor.data.redis.core.convert.RedisUpdate;
import lombok.Getter;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.core.mapping.RedisPersistentEntity;
import org.springframework.data.redis.core.mapping.RedisPersistentProperty;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @time 2024/6/29
 */
@Getter
@SuppressWarnings("deprecation")
public class RedisAdapter {


    private static final String SCAN_ = ":*";

    private final RedisOperations<byte[], byte[]> redisOps;
    private final RedisEntityConverter converter;

    public RedisAdapter(RedisOperations<byte[], byte[]> redisOps) {
        this(redisOps, new RedisEntityConverter(new RedisMappingContext()));
    }

    public RedisAdapter(RedisOperations<byte[], byte[]> redisOps, RedisEntityConverter converter) {
        this.redisOps = redisOps;
        this.converter = converter;
    }


    /**
     * 判断缓存中有没有这个key
     */
    public <T> boolean hasKey(Object id, String keyspace, Class<T> type) {


        byte[] redisKey = createKey(id, keyspace, type);

        return Boolean.TRUE.equals(redisOps.hasKey(redisKey));
    }


    /**
     * 刷新过期时间
     */
    public <T> void expire(Object id, String keyspace, Class<T> type, long timeout, TimeUnit timeUnit) {

        byte[] redisKey = createKey(id, keyspace, type);

        redisOps.expire(redisKey, timeout, timeUnit);
    }

    public <T> List<T> findByIdForList(Object id, String keyspace, Class<T> type) {

        byte[] redisKey = createKey(id, keyspace, type);

        List<byte[]> raws = redisOps.execute((RedisCallback<List<byte[]>>) connection -> connection.lRange(redisKey, 0, -1));

        RedisMetadata rdo = new RedisMetadata();

        rdo.setRaws(raws);

        return this.converter.readList(type, rdo);
    }

    public <T> Set<T> findByIdForSet(Object id, String keyspace, Class<T> type) {

        byte[] redisKey = createKey(id, keyspace, type);

        Set<byte[]> raws = redisOps.execute((RedisCallback<Set<byte[]>>) connection -> connection.sMembers(redisKey));

        RedisMetadata rdo = new RedisMetadata();

        rdo.setRaws(raws);

        return Set.copyOf(this.converter.readList(type, rdo));
    }


    public <T> T findById(Object id, String keyspace, Class<T> type) {
        RedisPersistentEntity<?> entity = this.converter.getMappingContext()
                .getRequiredPersistentEntity(type);

        byte[] redisKey = createKey(id, keyspace, type);

        byte[] raw = redisOps.execute((RedisCallback<byte[]>) connection -> connection.get(redisKey));

        if (null == raw) {
            return null;
        }

        RedisMetadata rdo = RedisMetadata.createRedisEntity(raw);

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


    public <T> List<T> findAll(String keyspace, Class<T> type) {

        byte[] redisKey = Strings.toBytesOrEmpty(keyspace + SCAN_);

        List<byte[]> raws = redisOps.execute((RedisCallback<List<byte[]>>) connection -> {
            List<byte[]> rawList = new ArrayList<>();

            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(redisKey).count(3000).build());

            if (cursor.hasNext()) {
                byte[] key = cursor.next();
                byte[] raw = connection.get(key);
                rawList.add(raw);
            }
            return rawList;
        });

        RedisMetadata rdo = new RedisMetadata();

        rdo.setRaws(raws);

        return this.converter.readList(type, rdo);
    }

    public <T> void delete(Object id, String keyspace, Class<T> type) {

        byte[] redisKey = createKey(id, keyspace, type);
        redisOps.execute((RedisCallback<Long>) connection -> connection.del(redisKey));

    }

    public void update(RedisUpdate<?> update) {

        byte[] redisKey = createKey(update.getId(), null, update.getTarget());

        RedisMetadata rdo = new RedisMetadata();
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

    @SuppressWarnings("deprecation")
    public boolean updateIfAbsent(RedisUpdate<?> update) {

        byte[] redisKey = createKey(update.getId(), null, update.getTarget());

        RedisMetadata rdo = new RedisMetadata();
        this.converter.write(update, rdo);


        return Boolean.TRUE.equals(redisOps.execute((RedisCallback<Boolean>) connection -> {

            if (update.isRefresh()) {
                if (rdo.getTimeToLive().isPresent()) {
                    Expiration expiration = Expiration.from(rdo.getTimeToLive().get(), TimeUnit.SECONDS);
                    return connection.set(redisKey, rdo.getRaw(), expiration, RedisStringCommands.SetOption.ifAbsent());
                }
            } else {
                connection.persist(redisKey);
                return connection.setNX(redisKey, rdo.getRaw());
            }
            return false;
        }));

    }

    public <T> byte[] createKey(Object id, String keyspace, Class<T> type) {

        RedisPersistentEntity<?> entity = this.converter.getMappingContext().getRequiredPersistentEntity(type);

        if (Strings.isEmpty(keyspace)) {
            keyspace = entity.getKeySpace();
        }

        return Strings.toBytes(Strings.splicingRedisKey(keyspace, this.converter.getConversionService().convert(id, String.class)));
    }

}
