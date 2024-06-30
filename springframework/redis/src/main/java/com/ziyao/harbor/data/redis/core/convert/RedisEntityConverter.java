package com.ziyao.harbor.data.redis.core.convert;

import lombok.Getter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.core.mapping.RedisPersistentEntity;
import org.springframework.data.util.TypeInformation;

/**
 * @author ziyao zhang
 * @time 2024/6/29
 */
@Getter
public class RedisEntityConverter {

    private final RedisMappingContext mappingContext;
    private final GenericConversionService conversionService;

    public RedisEntityConverter(RedisMappingContext mappingContext) {
        this.mappingContext = mappingContext;
        this.conversionService = new DefaultConversionService();
        this.conversionService.addConverter(new ObjectToBytesConverter());
        this.conversionService.addConverter(new BytesToObjectConverter());
    }

    public <R> R read(Class<R> type, RedisEntity source) {

        TypeInformation<?> readType = TypeInformation.of(type);

        Object convert1 = conversionService.convert(source.getRaw(), readType.getType());
        Object convert = this.conversionService.convert(source.getRaw(), Object.class);


        return type.cast(convert);

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

        writeToRaw(source, sink, entity.getTypeInformation());
    }

    private void writeRedisUpdate(RedisUpdate<?> update, RedisEntity sink) {

        RedisPersistentEntity<?> entity = mappingContext.getRequiredPersistentEntity(update.getTarget());

        write(update.getValue(), sink);

        if (update.isRefresh()) {

            Long timeToLive = entity.getTimeToLiveAccessor().getTimeToLive(update.getValue());
            sink.setTimeToLive(timeToLive);
        }
    }

    public void writeToRaw(Object value, RedisEntity sink, TypeInformation<?> typeHint) {

        if (value == null) {
            return;
        }
        sink.setRaw(toBytes(value));
    }

    public byte[] toBytes(Object source) {

        if (source instanceof byte[] bytes) {
            return bytes;
        }
        return conversionService.convert(source, byte[].class);
    }
}
