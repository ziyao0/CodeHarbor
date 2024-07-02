package com.ziyao.harbor.data.redis.core;

import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.data.redis.core.convert.BoostMappingRedisConverter;
import com.ziyao.harbor.data.redis.core.convert.BytesToMapConverter;
import com.ziyao.harbor.data.redis.core.convert.MapToBytesConverter;
import com.ziyao.harbor.data.redis.core.convert.RedisRawData;
import lombok.Getter;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.convert.PathIndexResolver;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.core.convert.ReferenceResolverImpl;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.core.mapping.RedisPersistentEntity;
import org.springframework.data.redis.core.mapping.RedisPersistentProperty;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
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
public class RedisOpsAdapter {

    private static final int PHANTOM_KEY_TTL = 300;

    private final RedisOperations<?, ?> redisOps;
    private final BoostMappingRedisConverter converter;
    private final RedisMappingContext mappingContext;


    public RedisOpsAdapter(RedisOperations<?, ?> redisOps) {
        Assert.notNull(redisOps, "RedisOperations must not be null");
        RedisMappingContext mappingContext = new RedisMappingContext();
        this.converter = new BoostMappingRedisConverter(mappingContext,
                new PathIndexResolver(mappingContext), new ReferenceResolverImpl(redisOps));
        this.converter.setCustomConversions(
                new RedisCustomConversions(List.of(new BytesToMapConverter(), new MapToBytesConverter())));
        this.converter.afterPropertiesSet();
        this.redisOps = redisOps;
        this.mappingContext = mappingContext;
    }

    public <T> T insert(T objectToInsert) {
        RedisPersistentEntity<?> entity = getKeyValuePersistentEntity(objectToInsert);

        Object id = entity.getIdentifierAccessor(objectToInsert).getIdentifier();

        return insert(id, objectToInsert);
    }

    public <T> T insert(Object id, T objectToInsert) {


        if (!(objectToInsert instanceof RedisRawData)) {

            RedisPersistentEntity<?> entity = converter.getMappingContext()
                    .getRequiredPersistentEntity(objectToInsert.getClass());

            RedisPersistentProperty idProperty = entity.getRequiredIdProperty();
            PersistentPropertyAccessor<T> propertyAccessor = entity.getPropertyAccessor(objectToInsert);

            if (propertyAccessor.getProperty(idProperty) == null) {

                propertyAccessor.setProperty(idProperty, id);
                return doInsert(id, propertyAccessor.getBean());
            }
        }

        return doInsert(id, objectToInsert);
    }

    public <T> T findById(Object id, String keyspace, Class<T> type) {

        String stringId = toString(id);
        byte[] binId = createKey(keyspace, stringId);

        RedisCallback<byte[]> command = connection -> connection.get(binId);

        byte[] raw = redisOps.execute(command);

        if (raw == null || raw.length == 0) {
            return null;
        }

        RedisRawData data = new RedisRawData(raw);

        data.setId(stringId);
        data.setKeyspace(keyspace);

        return readBackTimeToLiveIfSet(binId, converter.read(type, data));
    }

    public void deleteAllOf(Class<?> type) {

        Assert.notNull(type, "Type to delete must not be null");

        String keyspace = resolveKeySpace(type);

        redisOps.execute((RedisCallback<Void>) connection -> {
            connection.del(toBytes(keyspace));
            return null;
        });
    }

    public <T> List<T> findAllOf(String keyspace, Class<T> type, long offset, int rows) {

        byte[] binKeyspace = toBytes(keyspace);

        Set<byte[]> ids = redisOps.execute((RedisCallback<Set<byte[]>>) connection -> connection.sMembers(binKeyspace));

        if (Collections.isEmpty(ids) || ids.size() < offset) {
            return List.of();
        }
        List<T> result = new ArrayList<>();
        List<byte[]> keys = new ArrayList<>(ids);

        offset = Math.max(0, offset);

        if (rows > 0) {
            keys = keys.subList((int) offset, Math.min((int) offset + rows, keys.size()));
        }
        for (byte[] key : keys) {
            result.add(findById(key, keyspace, type));
        }
        return result;
    }

    public <T> void delete(Object id, String keyspace, Class<T> type) {

        byte[] binId = toBytes(id);
        byte[] binKeyspace = toBytes(keyspace);

        byte[] keyToDelete = createKey(keyspace, toString(id));

        redisOps.execute((RedisCallback<Void>) connection -> {

            connection.del(keyToDelete);
            connection.sRem(binKeyspace, binId);

            return null;
        });
    }

    public boolean contains(Object id, String keyspace) {

        RedisCallback<Boolean> command = connection -> connection.sIsMember(toBytes(keyspace), toBytes(id));

        return Boolean.TRUE.equals(this.redisOps.execute(command));
    }

    public boolean expire(Object id, String keyspace, long ttl) {
        byte[] redisKey = createKey(keyspace, toString(id));

        RedisCallback<Boolean> command = connection1 -> connection1.expire(redisKey, ttl);

        return Boolean.TRUE.equals(this.redisOps.execute(command));
    }

    private RedisPersistentEntity<?> getKeyValuePersistentEntity(Object objectToInsert) {
        return this.mappingContext.getRequiredPersistentEntity(ClassUtils.getUserClass(objectToInsert));
    }

    private String resolveKeySpace(Class<?> type) {
        return this.mappingContext.getRequiredPersistentEntity(type).getKeySpace();
    }

    public byte[] createKey(String keyspace, String id) {
        return toBytes(keyspace + ":" + id);
    }

    public byte[] toBytes(Object source) {
        return source instanceof byte[] bytes ? bytes
                : getConverter().getConversionService().convert(source, byte[].class);
    }

    private String toString(Object value) {
        return value instanceof String stringValue ? stringValue
                : getConverter().getConversionService().convert(value, String.class);
    }

    private <T> T readBackTimeToLiveIfSet(@Nullable byte[] key, @Nullable T target) {

        if (target == null || key == null) {
            return target;
        }

        RedisPersistentEntity<?> entity = this.converter.getMappingContext().getRequiredPersistentEntity(target.getClass());

        if (entity.hasExplictTimeToLiveProperty()) {

            RedisPersistentProperty ttlProperty = entity.getExplicitTimeToLiveProperty();

            if (ttlProperty == null) {
                return target;
            }

            TimeToLive ttl = ttlProperty.findAnnotation(TimeToLive.class);

            Long timeout = redisOps.execute((RedisCallback<Long>) connection -> {

                if (ttl == null) {
                    return null;
                }

                if (ObjectUtils.nullSafeEquals(TimeUnit.SECONDS, ttl.unit())) {
                    return connection.ttl(key);
                }

                return connection.pTtl(key, ttl.unit());
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

    private boolean expires(Long timeToLive) {
        return timeToLive != null && timeToLive > 0;
    }

    public <T> T execute(RedisCallback<T> callback) {
        return redisOps.execute(callback);
    }


    public <T> T put(Object id, T item, String keyspace) {

        RedisRawData rdo = new RedisRawData();

        converter.write(item, rdo);

        if (rdo.getId() == null) {
            rdo.setId(converter.getConversionService().convert(id, String.class));
        }
        redisOps.execute((RedisCallback<Object>) connection -> {

            byte[] key = toBytes(rdo.getId());
            byte[] objectKey = createKey(rdo.getKeyspace(), rdo.getId());

            boolean isNew = Boolean.TRUE.equals(connection.exists(objectKey));

            connection.set(objectKey, rdo.getRaw());

            if (isNew) {
                connection.sAdd(toBytes(rdo.getKeyspace()), key);
            }

            if (expires(rdo.getTimeToLive())) {
                connection.expire(objectKey, rdo.getTimeToLive());
            } else {
                connection.persist(objectKey);
            }
            return null;
        });
        return item;
    }


    public void update(PartialUpdate<?> update) {

        RedisPersistentEntity<?> entity = this.converter.getMappingContext()
                .getRequiredPersistentEntity(update.getTarget());

        String keyspace = entity.getKeySpace();
        Object id = update.getId();

        byte[] redisKey = createKey(keyspace, converter.getConversionService().convert(id, String.class));

        RedisRawData rdo = new RedisRawData();
        this.converter.write(update, rdo);

        redisOps.execute((RedisCallback<Void>) connection -> {

            if (!rdo.getBucket().isEmpty()) {
                if (rdo.getBucket().size() > 1
                        || (rdo.getBucket().size() == 1 && !rdo.getBucket().asMap().containsKey("_class"))) {
                    connection.set(redisKey, rdo.getRaw());
                }
            }

            if (update.isRefreshTtl()) {
                if (expires(rdo.getTimeToLive())) {
                    connection.expire(redisKey, rdo.getTimeToLive());
                } else {
                    connection.persist(redisKey);
                }
            }
            return null;
        });
    }

    private <T> T doInsert(Object id, T objectToInsert) {
        Assert.notNull(id, "Id for object to be inserted must not be null");
        Assert.notNull(objectToInsert, "Object to be inserted must not be null");

        String keyspace = resolveKeySpace(objectToInsert.getClass());

        return this.put(id, objectToInsert, keyspace);
    }

}
