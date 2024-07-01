package com.ziyao.harbor.data.redis.core.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziyao.harbor.data.redis.jackson2.Jackson2Modules;
import lombok.Getter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.core.mapping.RedisPersistentEntity;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * @author ziyao zhang
 * @time 2024/6/29
 */
@Getter
public class RedisEntityConverter {

    private final RedisMappingContext mappingContext;
    private final GenericConversionService conversionService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisEntityConverter(RedisMappingContext mappingContext) {
        this.mappingContext = mappingContext;
        this.conversionService = new DefaultConversionService();

        ClassLoader classLoader = RedisEntityConverter.class.getClassLoader();
        List<Module> modules = Jackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(modules);
    }

    public <R> R read(Class<R> type, RedisEntity source) {

        try {
            byte[] raw = source.getRaw();

            if (isEmpty(raw)) {
                return null;
            }

            return this.objectMapper.readValue(source.getRaw(), type);

        } catch (Exception ex) {
            throw new SerializationException("Failed to deserialize byte array to object : " + ex.getMessage(), ex);
        }

    }

    public <R> List<R> readList(Class<R> type, RedisEntity source) {

        try {
            Collection<byte[]> raws = source.getRaws();

            if (raws == null || raws.isEmpty()) {
                return List.of();
            }

            List<R> result = new ArrayList<>(raws.size());
            for (byte[] raw : raws) {
                if (isEmpty(raw)) {
                    return null;
                }
                result.add(this.objectMapper.readValue(raw, type));
            }

            return result;

        } catch (Exception ex) {
            throw new SerializationException("Failed to deserialize byte array to object : " + ex.getMessage(), ex);
        }
    }

    public <HK, HV> Map<HK, HV> readList(Class<HK> keyType, Class<HV> valueType, RedisEntity source) {

        try {

            Map<byte[], byte[]> rawMap = source.getRawMap();

            if (rawMap == null || rawMap.isEmpty()) {
                return Map.of();
            }

            return Map.of();

        } catch (Exception ex) {
            throw new SerializationException("Failed to deserialize byte array to object : " + ex.getMessage(), ex);
        }
    }

    public void write(Object source, RedisEntity sink) {

        if (source instanceof RedisUpdate<?> update) {
            writeRedisUpdate(update, sink);
            return;
        }
        RedisPersistentEntity<?> entity = mappingContext.getPersistentEntity(source.getClass());

        if (entity == null) {
            sink.setRaw(new byte[]{});
            return;
        }

        sink.setKeyspace(entity.getKeySpace());

        Object identifier = entity.getIdentifierAccessor(source).getIdentifier();

        if (identifier != null) {
            sink.setId(getConversionService().convert(identifier, String.class));
        }
        Long ttl = entity.getTimeToLiveAccessor().getTimeToLive(source);
        if (ttl != null && ttl > 0) {
            sink.setTimeToLive(ttl);
        }

        writeToRaw(source, sink);
        if (source instanceof Collection<?> sourceCollection) {

            List<byte[]> raws = new ArrayList<>();
            sourceCollection.forEach(item -> raws.add(toBytes(item)));
            sink.setRaws(raws);
        }

        if (source instanceof Map<?, ?> sourceMap) {
            Map<byte[], byte[]> rawMap = new HashMap<>();
            sourceMap.forEach((k, v) -> rawMap.put(toBytes(k), toBytes(v)));
            sink.setRawMap(rawMap);
        }
    }

    private void writeRedisUpdate(RedisUpdate<?> update, RedisEntity sink) {

        RedisPersistentEntity<?> entity = mappingContext.getRequiredPersistentEntity(update.getTarget());

        write(update.getValue(), sink);

        if (update.isRefresh()) {

            Long timeToLive = entity.getTimeToLiveAccessor().getTimeToLive(update.getValue());
            sink.setTimeToLive(timeToLive);
        }
    }

    public void writeToRaw(Object value, RedisEntity sink) {

        if (value == null) {
            return;
        }
        sink.setRaw(toBytes(value));


    }

    public byte[] toBytes(Object source) {

        if (source instanceof byte[] bytes) {
            return bytes;
        }
//        return conversionService.convert(source, byte[].class);
        try {
            return this.objectMapper.writeValueAsBytes(source);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Failed to serialize object to byte array : " + e.getMessage(), e);
        }
    }

    static boolean isEmpty(@Nullable byte[] data) {
        return (data == null || data.length == 0);
    }

}
