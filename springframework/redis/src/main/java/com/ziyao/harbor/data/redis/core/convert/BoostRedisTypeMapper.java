package com.ziyao.harbor.data.redis.core.convert;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.convert.*;
import org.springframework.data.mapping.Alias;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * @author ziyao zhang
 * @time 2024/7/2
 */
public class BoostRedisTypeMapper extends DefaultTypeMapper<Bucket2.BucketPropertyPath> implements TypeMapper<Bucket2.BucketPropertyPath> {

    public static final String DEFAULT_TYPE_KEY = "_class";

    private final @Nullable String typeKey;

    public BoostRedisTypeMapper() {
        this(DEFAULT_TYPE_KEY);
    }

    public BoostRedisTypeMapper(@Nullable String typeKey) {
        this(typeKey, Collections.singletonList(new SimpleTypeInformationMapper()));
    }

    public BoostRedisTypeMapper(@Nullable String typeKey,
                                MappingContext<? extends PersistentEntity<?, ?>, ?> mappingContext) {
        this(typeKey, new BoostRedisTypeMapper.BucketTypeAliasAccessor(typeKey, getConversionService()), mappingContext,
                Collections.singletonList(new SimpleTypeInformationMapper()));
    }

    public BoostRedisTypeMapper(@Nullable String typeKey, List<? extends TypeInformationMapper> mappers) {
        this(typeKey, new BoostRedisTypeMapper.BucketTypeAliasAccessor(typeKey, getConversionService()), null, mappers);
    }

    private BoostRedisTypeMapper(@Nullable String typeKey, TypeAliasAccessor<Bucket2.BucketPropertyPath> accessor,
                                 @Nullable MappingContext<? extends PersistentEntity<?, ?>, ?> mappingContext,
                                 List<? extends TypeInformationMapper> mappers) {

        super(accessor, mappingContext, mappers);

        this.typeKey = typeKey;
    }

    private static GenericConversionService getConversionService() {

        GenericConversionService conversionService = new GenericConversionService();
        new RedisCustomConversions().registerConvertersIn(conversionService);

        return conversionService;
    }

    public boolean isTypeKey(@Nullable String key) {
        return key != null && typeKey != null && key.endsWith(typeKey);
    }

    static final class BucketTypeAliasAccessor implements TypeAliasAccessor<Bucket2.BucketPropertyPath> {

        private final @Nullable String typeKey;

        private final ConversionService conversionService;

        BucketTypeAliasAccessor(@Nullable String typeKey, ConversionService conversionService) {

            Assert.notNull(conversionService, "ConversionService must not be null");

            this.typeKey = typeKey;
            this.conversionService = conversionService;
        }

        public @NonNull Alias readAliasFrom(@NonNull Bucket2.BucketPropertyPath source) {

            if (typeKey == null || source instanceof List) {
                return Alias.NONE;
            }
            Object obj = source.get(typeKey);

            if (obj != null) {
                return Alias.ofNullable(conversionService.convert(obj, String.class));
            }

            return Alias.NONE;
        }

        public void writeTypeTo(@NonNull Bucket2.BucketPropertyPath sink, @NonNull Object alias) {

            if (typeKey != null) {
                sink.put(typeKey, alias);
            }
        }
    }
}
