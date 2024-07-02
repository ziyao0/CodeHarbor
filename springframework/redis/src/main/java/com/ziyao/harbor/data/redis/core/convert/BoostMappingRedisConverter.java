package com.ziyao.harbor.data.redis.core.convert;

import com.ziyao.harbor.core.utils.Strings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.CollectionFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.convert.EntityConverter;
import org.springframework.data.mapping.*;
import org.springframework.data.mapping.model.EntityInstantiator;
import org.springframework.data.mapping.model.EntityInstantiators;
import org.springframework.data.mapping.model.PersistentEntityParameterValueProvider;
import org.springframework.data.mapping.model.PropertyValueProvider;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.convert.*;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.core.mapping.RedisPersistentEntity;
import org.springframework.data.redis.core.mapping.RedisPersistentProperty;
import org.springframework.data.redis.util.ByteUtils;
import org.springframework.data.util.ProxyUtils;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * copy to {@link MappingRedisConverter}
 *
 * @author ziyao
 * @see <a href="https://blog.zziyao.cn">https://blog.zziyao.cn</a>
 * @see MappingRedisConverter
 * @since 2024/07/02 16:26:52
 */
public class BoostMappingRedisConverter
        implements EntityConverter<RedisPersistentEntity<?>, RedisPersistentProperty, Object, RedisRawData> {


    private static final String INVALID_TYPE_ASSIGNMENT = "Value of type %s cannot be assigned to property %s of type %s";

    private final RedisMappingContext mappingContext;
    private final GenericConversionService conversionService;
    @Getter
    private final EntityInstantiators entityInstantiators;
    private final RedisTypeMapper typeMapper;
    private final Comparator<String> listKeyComparator = Comparator.nullsFirst(NaturalOrderingKeyComparator.INSTANCE);

    @Setter
    private IndexResolver indexResolver;
    @Setter
    private @Nullable ReferenceResolver referenceResolver;
    private CustomConversions customConversions;

    /**
     * Creates new {@link BoostMappingRedisConverter}.
     *
     * @param context can be {@literal null}.
     * @since 2.4
     */
    public BoostMappingRedisConverter(RedisMappingContext context) {
        this(context, null, null, null);
    }

    /**
     * Creates new {@link BoostMappingRedisConverter} and defaults {@link RedisMappingContext} when {@literal null}.
     *
     * @param mappingContext    can be {@literal null}.
     * @param indexResolver     can be {@literal null}.
     * @param referenceResolver can be not be {@literal null}.
     */
    public BoostMappingRedisConverter(@Nullable RedisMappingContext mappingContext, @Nullable IndexResolver indexResolver,
                                      @Nullable ReferenceResolver referenceResolver) {
        this(mappingContext, indexResolver, referenceResolver, null);
    }

    /**
     * Creates new {@link BoostMappingRedisConverter} and defaults {@link RedisMappingContext} when {@literal null}.
     *
     * @param mappingContext    can be {@literal null}.
     * @param indexResolver     can be {@literal null}.
     * @param referenceResolver can be {@literal null}.
     * @param typeMapper        can be {@literal null}.
     * @since 2.1
     */
    public BoostMappingRedisConverter(@Nullable RedisMappingContext mappingContext, @Nullable IndexResolver indexResolver,
                                      @Nullable ReferenceResolver referenceResolver, @Nullable RedisTypeMapper typeMapper) {

        this.mappingContext = mappingContext != null ? mappingContext : new RedisMappingContext();

        List<Converter<?, ?>> converters = List.of(new BytesToMapConverter(), new MapToBytesConverter());

        this.entityInstantiators = new EntityInstantiators();
        this.conversionService = new DefaultConversionService();
        this.conversionService.addConverter(new BytesToMapConverter());
        this.conversionService.addConverter(new MapToBytesConverter());
        this.customConversions = new RedisCustomConversions(converters);

        this.typeMapper = typeMapper != null ? typeMapper
                : new DefaultRedisTypeMapper(DefaultRedisTypeMapper.DEFAULT_TYPE_KEY, this.mappingContext);

        this.indexResolver = indexResolver != null ? indexResolver : new PathIndexResolver(this.mappingContext);
        this.referenceResolver = referenceResolver;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R read(@Nullable Class<R> type, RedisRawData source) {


        Map<String, Object> rawMap = this.getConversionService().convert(source.getRaw(), Map.class);


        TypeInformation<?> readType = typeMapper.readType(source.getBucket().getPath(), TypeInformation.of(type));

        return readType.isCollectionLike()
                ? (R) readCollectionOrArray("", ArrayList.class, Object.class, source.getBucket())
                : doReadInternal("", type, source);

    }

    @Nullable
    private <R> R readInternal(String path, Class<R> type, RedisRawData source) {
        return source.getBucket().isEmpty() ? null : doReadInternal(path, type, source);
    }

    @SuppressWarnings("unchecked")
    private <R> R doReadInternal(String path, Class<R> type, RedisRawData source) {

        TypeInformation<?> readType = typeMapper.readType(source.getBucket().getPath(), TypeInformation.of(type));

        if (customConversions.hasCustomReadTarget(Map.class, readType.getType())) {

            Map<String, Object> partial = new HashMap<>();

            if (!path.isEmpty()) {

                for (Map.Entry<String, Object> entry : source.getBucket().extract(path + ".").entrySet()) {
                    partial.put(entry.getKey().substring(path.length() + 1), entry.getValue());
                }

            } else {
                partial.putAll(source.getBucket().asMap());
            }
            R instance = (R) conversionService.convert(partial, readType.getType());

            RedisPersistentEntity<?> entity = mappingContext.getPersistentEntity(readType);
            if (entity != null && entity.hasIdProperty()) {

                PersistentPropertyAccessor<R> propertyAccessor = entity.getPropertyAccessor(instance);

                propertyAccessor.setProperty(entity.getRequiredIdProperty(), source.getId());
                instance = propertyAccessor.getBean();
            }
            return instance;
        }

        if (conversionService.canConvert(byte[].class, readType.getType())) {
            return (R) conversionService.convert(source.getBucket().get(StringUtils.hasText(path) ? path : "_raw"),
                    readType.getType());
        }

        RedisPersistentEntity<?> entity = mappingContext.getRequiredPersistentEntity(readType);
        EntityInstantiator instantiator = entityInstantiators.getInstantiatorFor(entity);

        Object instance = instantiator.createInstance((RedisPersistentEntity<RedisPersistentProperty>) entity,
                new PersistentEntityParameterValueProvider<>(entity,
                        new BoostMappingRedisConverter.ConverterAwareParameterValueProvider(path, source, conversionService), this.conversionService));

        PersistentPropertyAccessor<Object> accessor = entity.getPropertyAccessor(instance);

        entity.doWithProperties((PropertyHandler<RedisPersistentProperty>) persistentProperty -> {

            InstanceCreatorMetadata<RedisPersistentProperty> creator = entity.getInstanceCreatorMetadata();

            if (creator != null && creator.isCreatorParameter(persistentProperty)) {
                return;
            }

            Object targetValue = readProperty(path, source, persistentProperty);

            if (targetValue != null) {
                accessor.setProperty(persistentProperty, targetValue);
            }
        });

        readAssociation(path, source, entity, accessor);

        return (R) accessor.getBean();
    }

    @Nullable
    protected Object readProperty(String path, RedisRawData source, RedisPersistentProperty persistentProperty) {

        String currentPath = !path.isEmpty() ? path + "." + persistentProperty.getName() : persistentProperty.getName();
        TypeInformation<?> typeInformation = typeMapper.readType(source.getBucket().getPropertyPath(currentPath),
                persistentProperty.getTypeInformation());

        if (typeInformation.isMap()) {

            Class<?> mapValueType = null;

            if (typeInformation.getMapValueType() != null) {
                mapValueType = typeInformation.getMapValueType().getType();
            }

            if (mapValueType == null && persistentProperty.isMap()) {
                mapValueType = persistentProperty.getMapValueType();
            }

            if (mapValueType == null) {
                throw new IllegalArgumentException("Unable to retrieve MapValueType");
            }

            if (conversionService.canConvert(byte[].class, mapValueType)) {
                return readMapOfSimpleTypes(currentPath, typeInformation.getType(),
                        typeInformation.getRequiredComponentType().getType(), mapValueType, source);
            }

            return readMapOfComplexTypes(currentPath, typeInformation.getType(),
                    typeInformation.getRequiredComponentType().getType(), mapValueType, source);
        }

        if (typeInformation.isCollectionLike()) {

            if (!isByteArray(typeInformation)) {

                return readCollectionOrArray(currentPath, typeInformation.getType(),
                        typeInformation.getRequiredComponentType().getType(), source.getBucket());
            }

            if (!source.getBucket().hasValue(currentPath) && isByteArray(typeInformation)) {

                return readCollectionOrArray(currentPath, typeInformation.getType(),
                        typeInformation.getRequiredComponentType().getType(), source.getBucket());
            }
        }

        if (mappingContext.getPersistentEntity(typeInformation) != null
                && !conversionService.canConvert(byte[].class, typeInformation.getRequiredActualType().getType())) {

            Bucket bucket = source.getBucket().extract(currentPath + ".");

            RedisRawData newBucket = new RedisRawData(bucket);

            return readInternal(currentPath, typeInformation.getType(), newBucket);
        }

        byte[] sourceBytes = source.getBucket().get(currentPath);

        if (typeInformation.getType().isPrimitive() && sourceBytes == null) {
            return null;
        }

        if (persistentProperty.isIdProperty() && ObjectUtils.isEmpty(path)) {
            return sourceBytes != null ? fromBytes(sourceBytes, typeInformation.getType()) : source.getId();
        }

        if (sourceBytes == null) {
            return null;
        }

        if (customConversions.hasCustomReadTarget(byte[].class, persistentProperty.getType())) {
            return fromBytes(sourceBytes, persistentProperty.getType());
        }

        Class<?> typeToUse = getTypeHint(currentPath, source.getBucket(), persistentProperty.getType());
        return fromBytes(sourceBytes, typeToUse);
    }

    private void readAssociation(String path, RedisRawData source, RedisPersistentEntity<?> entity,
                                 PersistentPropertyAccessor<?> accessor) {

        entity.doWithAssociations((AssociationHandler<RedisPersistentProperty>) association -> {

            String currentPath = !path.isEmpty() ? path + "." + association.getInverse().getName()
                    : association.getInverse().getName();

            if (association.getInverse().isCollectionLike()) {

                Bucket bucket = source.getBucket().extract(currentPath + ".[");

                Collection<Object> target = CollectionFactory.createCollection(association.getInverse().getType(),
                        association.getInverse().getComponentType(), bucket.size());

                for (Map.Entry<String, byte[]> entry : bucket.entrySet()) {

                    String referenceKey = fromBytes(entry.getValue(), String.class);

                    if (!BoostMappingRedisConverter.KeyspaceIdentifier.isValid(referenceKey)) {
                        continue;
                    }

                    BoostMappingRedisConverter.KeyspaceIdentifier identifier = BoostMappingRedisConverter.KeyspaceIdentifier.of(referenceKey);
                    Map<byte[], byte[]> rawHash = referenceResolver.resolveReference(identifier.getId(),
                            identifier.getKeyspace());

                    if (!CollectionUtils.isEmpty(rawHash)) {
                        target.add(read(association.getInverse().getActualType(), new RedisRawData(rawHash)));
                    }
                }

                accessor.setProperty(association.getInverse(), target);

            } else {

                byte[] binKey = source.getBucket().get(currentPath);
                if (binKey == null || binKey.length == 0) {
                    return;
                }

                String referenceKey = fromBytes(binKey, String.class);
                if (BoostMappingRedisConverter.KeyspaceIdentifier.isValid(referenceKey)) {

                    BoostMappingRedisConverter.KeyspaceIdentifier identifier = BoostMappingRedisConverter.KeyspaceIdentifier.of(referenceKey);

                    Map<byte[], byte[]> rawHash = referenceResolver.resolveReference(identifier.getId(),
                            identifier.getKeyspace());

                    if (!CollectionUtils.isEmpty(rawHash)) {
                        accessor.setProperty(association.getInverse(),
                                read(association.getInverse().getActualType(), new RedisRawData(rawHash)));
                    }
                }
            }
        });
    }

    @Override
    @SuppressWarnings({"rawtypes"})
    public void write(@Nullable Object source, @NonNull RedisRawData sink) {

        if (source == null) {
            return;
        }

        if (source instanceof PartialUpdate) {
            writePartialUpdate((PartialUpdate) source, sink);
            return;
        }

        RedisPersistentEntity<?> entity = mappingContext.getPersistentEntity(source.getClass());

        if (!customConversions.hasCustomWriteTarget(source.getClass())) {
            typeMapper.writeType(ClassUtils.getUserClass(source), sink.getBucket().getPath());
        }

        if (entity == null) {

            typeMapper.writeType(ClassUtils.getUserClass(source), sink.getBucket().getPath());
            sink.getBucket().put("_raw", conversionService.convert(source, byte[].class));
            return;
        }

        sink.setKeyspace(entity.getKeySpace());

        if (entity.getTypeInformation().isCollectionLike()) {
            writeCollection(entity.getKeySpace(), "", (List) source, entity.getTypeInformation().getRequiredComponentType(),
                    sink);
        } else {
            writeInternal(entity.getKeySpace(), "", source, entity.getTypeInformation(), sink);
        }

        Object identifier = entity.getIdentifierAccessor(source).getIdentifier();

        if (identifier != null) {
            sink.setId(getConversionService().convert(identifier, String.class));
        }

        Long ttl = entity.getTimeToLiveAccessor().getTimeToLive(source);
        if (ttl != null && ttl > 0) {
            sink.setTimeToLive(ttl);
        }

        for (IndexedData indexedData : indexResolver.resolveIndexesFor(entity.getTypeInformation(), source)) {
            sink.addIndexedData(indexedData);
        }

        byte[] raw = this.getConversionService().convert(sink.getBucket().rawMap(), byte[].class);

        sink.setRaw(raw);
    }

    protected void writePartialUpdate(PartialUpdate<?> update, RedisRawData sink) {

        RedisPersistentEntity<?> entity = mappingContext.getRequiredPersistentEntity(update.getTarget());

        write(update.getValue(), sink);

        for (String key : sink.getBucket().keySet()) {
            if (typeMapper.isTypeKey(key)) {
                sink.getBucket().remove(key);
                break;
            }
        }

        if (update.isRefreshTtl() && !update.getPropertyUpdates().isEmpty()) {

            Long ttl = entity.getTimeToLiveAccessor().getTimeToLive(update);
            if (ttl != null && ttl > 0) {
                sink.setTimeToLive(ttl);
            }
        }

        for (PartialUpdate.PropertyUpdate pUpdate : update.getPropertyUpdates()) {

            String path = pUpdate.getPropertyPath();

            if (PartialUpdate.UpdateCommand.SET.equals(pUpdate.getCmd())) {
                writePartialPropertyUpdate(update, pUpdate, sink, entity, path);
            }
        }
    }


    private void writePartialPropertyUpdate(PartialUpdate<?> update, PartialUpdate.PropertyUpdate pUpdate, RedisRawData sink,
                                            RedisPersistentEntity<?> entity, String path) {

        RedisPersistentProperty targetProperty = getTargetPropertyOrNullForPath(path, update.getTarget());

        if (targetProperty == null) {

            targetProperty = getTargetPropertyOrNullForPath(path.replaceAll("\\.\\[.*]", ""), update.getTarget());

            TypeInformation<?> ti = targetProperty == null ? TypeInformation.OBJECT
                    : (targetProperty.isMap() ? (targetProperty.getTypeInformation().getMapValueType() != null
                    ? targetProperty.getTypeInformation().getRequiredMapValueType()
                    : TypeInformation.OBJECT) : targetProperty.getTypeInformation().getActualType());

            writeInternal(entity.getKeySpace(), pUpdate.getPropertyPath(), pUpdate.getValue(), ti, sink);
            return;
        }

        if (targetProperty.isAssociation()) {

            if (targetProperty.isCollectionLike()) {

                RedisPersistentEntity<?> ref = mappingContext.getPersistentEntity(targetProperty.getRequiredAssociation()
                        .getInverse().getTypeInformation().getRequiredComponentType().getRequiredActualType());

                int i = 0;
                for (Object o : (Collection<?>) pUpdate.getValue()) {

                    Object refId = ref.getPropertyAccessor(o).getProperty(ref.getRequiredIdProperty());
                    if (refId != null) {
                        sink.getBucket().put(pUpdate.getPropertyPath() + ".[" + i + "]", toBytes(ref.getKeySpace() + ":" + refId));
                        i++;
                    }
                }
            } else {

                RedisPersistentEntity<?> ref = mappingContext
                        .getRequiredPersistentEntity(targetProperty.getRequiredAssociation().getInverse().getTypeInformation());

                Object refId = ref.getPropertyAccessor(pUpdate.getValue()).getProperty(ref.getRequiredIdProperty());
                if (refId != null) {
                    sink.getBucket().put(pUpdate.getPropertyPath(), toBytes(ref.getKeySpace() + ":" + refId));
                }
            }
        } else if (targetProperty.isCollectionLike() && isNotByteArray(targetProperty)) {

            Collection<?> collection = pUpdate.getValue() instanceof Collection ? (Collection<?>) pUpdate.getValue()
                    : Collections.singleton(pUpdate.getValue());
            writeCollection(entity.getKeySpace(), pUpdate.getPropertyPath(), collection,
                    targetProperty.getTypeInformation().getRequiredActualType(), sink);
        } else if (targetProperty.isMap()) {

            Map<Object, Object> map = new HashMap<>();

            if (pUpdate.getValue() instanceof Map) {
                map.putAll((Map<?, ?>) pUpdate.getValue());
            } else if (pUpdate.getValue() instanceof Map.Entry) {
                map.put(((Map.Entry<?, ?>) pUpdate.getValue()).getKey(), ((Map.Entry<?, ?>) pUpdate.getValue()).getValue());
            } else {
                throw new MappingException(
                        String.format("Cannot set update value for map property '%s' to '%s'; Please use a Map or Map.Entry",
                                pUpdate.getPropertyPath(), pUpdate.getValue()));
            }

            writeMap(entity.getKeySpace(), pUpdate.getPropertyPath(), targetProperty.getMapValueType(), map, sink);
        } else {

            writeInternal(entity.getKeySpace(), pUpdate.getPropertyPath(), pUpdate.getValue(),
                    targetProperty.getTypeInformation(), sink);

            Set<IndexedData> data = indexResolver.resolveIndexesFor(entity.getKeySpace(), pUpdate.getPropertyPath(),
                    targetProperty.getTypeInformation(), pUpdate.getValue());

            if (data.isEmpty()) {

                data = indexResolver.resolveIndexesFor(entity.getKeySpace(), pUpdate.getPropertyPath(),
                        targetProperty.getOwner().getTypeInformation(), pUpdate.getValue());

            }
            sink.addIndexedData(data);
        }
    }

    @Nullable
    RedisPersistentProperty getTargetPropertyOrNullForPath(String path, Class<?> type) {

        try {

            PersistentPropertyPath<RedisPersistentProperty> persistentPropertyPath = mappingContext
                    .getPersistentPropertyPath(path, type);
            return persistentPropertyPath.getLeafProperty();
        } catch (Exception ignore) {
        }

        return null;
    }


    private void writeInternal(@Nullable String keyspace, String path, @Nullable Object value,
                               TypeInformation<?> typeHint, RedisRawData sink) {

        if (value == null) {
            return;
        }

        if (customConversions.hasCustomWriteTarget(value.getClass())) {

            Optional<Class<?>> targetType = customConversions.getCustomWriteTarget(value.getClass());

            if (!StringUtils.hasText(path) && targetType.isPresent()
                    && ClassUtils.isAssignable(byte[].class, targetType.get())) {
                sink.getBucket().put(StringUtils.hasText(path) ? path : "_raw", conversionService.convert(value, byte[].class));
            } else {

                if (!ClassUtils.isAssignable(typeHint.getType(), value.getClass())) {
                    throw new MappingException(
                            String.format(INVALID_TYPE_ASSIGNMENT, value.getClass(), path, typeHint.getType()));
                }
                writeToBucket(path, value, sink, typeHint.getType());
            }
            return;
        }

        if (value instanceof byte[] valueBytes) {
            sink.getBucket().put(StringUtils.hasText(path) ? path : "_raw", valueBytes);
            return;
        }

        if (value.getClass() != typeHint.getType()) {
            typeMapper.writeType(value.getClass(), sink.getBucket().getPropertyPath(path));
        }

        RedisPersistentEntity<?> entity = mappingContext.getRequiredPersistentEntity(value.getClass());
        PersistentPropertyAccessor<Object> accessor = entity.getPropertyAccessor(value);

        entity.doWithProperties((PropertyHandler<RedisPersistentProperty>) persistentProperty -> {

            String propertyStringPath = (!path.isEmpty() ? path + "." : "") + persistentProperty.getName();

            Object propertyValue = accessor.getProperty(persistentProperty);
            if (persistentProperty.isIdProperty()) {

                if (propertyValue != null) {
                    sink.getBucket().put(propertyStringPath, toBytes(propertyValue));
                }
                return;
            }

            if (persistentProperty.isMap()) {

                if (propertyValue != null) {
                    writeMap(keyspace, propertyStringPath, persistentProperty.getMapValueType(), (Map<?, ?>) propertyValue, sink);
                }
            } else if (persistentProperty.isCollectionLike() && isNotByteArray(persistentProperty)) {

                if (propertyValue == null) {
                    writeCollection(keyspace, propertyStringPath, null,
                            persistentProperty.getTypeInformation().getRequiredComponentType(), sink);
                } else {

                    if (Iterable.class.isAssignableFrom(propertyValue.getClass())) {

                        writeCollection(keyspace, propertyStringPath, (Iterable<?>) propertyValue,
                                persistentProperty.getTypeInformation().getRequiredComponentType(), sink);
                    } else if (propertyValue.getClass().isArray()) {

                        writeCollection(keyspace, propertyStringPath, CollectionUtils.arrayToList(propertyValue),
                                persistentProperty.getTypeInformation().getRequiredComponentType(), sink);
                    } else {

                        throw new RuntimeException("Don't know how to handle " + propertyValue.getClass() + " type collection");
                    }
                }

            } else if (propertyValue != null) {

                if (customConversions.isSimpleType(ProxyUtils.getUserClass(propertyValue.getClass()))) {

                    writeToBucket(propertyStringPath, propertyValue, sink, persistentProperty.getType());
                } else {
                    writeInternal(keyspace, propertyStringPath, propertyValue,
                            persistentProperty.getTypeInformation().getRequiredActualType(), sink);
                }
            }
        });

        writeAssociation(path, entity, value, sink);
    }

    private void writeAssociation(String path, RedisPersistentEntity<?> entity, @Nullable Object value, RedisRawData sink) {

        if (value == null) {
            return;
        }

        PersistentPropertyAccessor<Object> accessor = entity.getPropertyAccessor(value);

        entity.doWithAssociations((AssociationHandler<RedisPersistentProperty>) association -> {

            Object refObject = accessor.getProperty(association.getInverse());
            if (refObject == null) {
                return;
            }

            String pv = !path.isEmpty() ? path + "." : "";
            if (association.getInverse().isCollectionLike()) {

                RedisPersistentEntity<?> ref = mappingContext.getRequiredPersistentEntity(
                        association.getInverse().getTypeInformation().getRequiredComponentType().getRequiredActualType());

                String keyspace = ref.getKeySpace();
                String propertyStringPath = pv + association.getInverse().getName();

                int i = 0;
                for (Object o : (Collection<?>) refObject) {

                    Object refId = ref.getPropertyAccessor(o).getProperty(ref.getRequiredIdProperty());
                    if (refId != null) {
                        sink.getBucket().put(propertyStringPath + ".[" + i + "]", toBytes(keyspace + ":" + refId));
                        i++;
                    }
                }

            } else {

                RedisPersistentEntity<?> ref = mappingContext
                        .getRequiredPersistentEntity(association.getInverse().getTypeInformation());
                String keyspace = ref.getKeySpace();

                if (keyspace != null) {
                    Object refId = ref.getPropertyAccessor(refObject).getProperty(ref.getRequiredIdProperty());

                    if (refId != null) {
                        String propertyStringPath = pv + association.getInverse().getName();
                        sink.getBucket().put(propertyStringPath, toBytes(keyspace + ":" + refId));
                    }
                }
            }
        });
    }


    private void writeCollection(@Nullable String keyspace, String path, @Nullable Iterable<?> values,
                                 TypeInformation<?> typeHint, RedisRawData sink) {

        if (values == null) {
            return;
        }

        int i = 0;
        for (Object value : values) {

            if (value == null) {
                break;
            }

            String currentPath = path + (path.isEmpty() ? "" : ".") + "[" + i + "]";

            if (!ClassUtils.isAssignable(typeHint.getType(), value.getClass())) {
                throw new MappingException(
                        String.format(INVALID_TYPE_ASSIGNMENT, value.getClass(), currentPath, typeHint.getType()));
            }

            if (customConversions.hasCustomWriteTarget(value.getClass())) {
                writeToBucket(currentPath, value, sink, typeHint.getType());
            } else {
                writeInternal(keyspace, currentPath, value, typeHint, sink);
            }
            i++;
        }
    }

    private void writeToBucket(String path, @Nullable Object value, RedisRawData sink, Class<?> propertyType) {

        if (value == null || (value instanceof Optional && ((Optional<?>) value).isEmpty())) {
            return;
        }

        if (value instanceof byte[]) {
            sink.getBucket().put(path, toBytes(value));
            return;
        }

        if (customConversions.hasCustomWriteTarget(value.getClass())) {

            Optional<Class<?>> targetType = customConversions.getCustomWriteTarget(value.getClass());

            if (!propertyType.isPrimitive() && targetType.filter(it -> ClassUtils.isAssignable(Map.class, it)).isEmpty()
                    && customConversions.isSimpleType(value.getClass()) && value.getClass() != propertyType) {
                typeMapper.writeType(value.getClass(), sink.getBucket().getPropertyPath(path));
            }

            if (targetType.filter(it -> ClassUtils.isAssignable(Map.class, it)).isPresent()) {

                Map<?, ?> map = (Map<?, ?>) conversionService.convert(value, targetType.get());
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    sink.getBucket().put(path + (StringUtils.hasText(path) ? "." : "") + entry.getKey(),
                            toBytes(entry.getValue()));
                }
            } else if (targetType.filter(it -> ClassUtils.isAssignable(byte[].class, it)).isPresent()) {
                sink.getBucket().put(path, toBytes(value));
            } else {
                throw new IllegalArgumentException(
                        String.format("Cannot convert value '%s' of type %s to bytes", value, value.getClass()));
            }
        }
    }

    @Nullable
    private Object readCollectionOrArray(String path, Class<?> collectionType, Class<?> valueType, Bucket bucket) {

        List<String> keys = new ArrayList<>(bucket.extractAllKeysFor(path));
        keys.sort(listKeyComparator);

        boolean isArray = collectionType.isArray();
        Class<?> collectionTypeToUse = isArray ? ArrayList.class : collectionType;
        Collection<Object> target = CollectionFactory.createCollection(collectionTypeToUse, valueType, keys.size());

        for (String key : keys) {

            if (typeMapper.isTypeKey(key)) {
                continue;
            }

            Bucket elementData = bucket.extract(key);

            TypeInformation<?> typeInformation = typeMapper.readType(elementData.getPropertyPath(key),
                    TypeInformation.of(valueType));

            Class<?> typeToUse = typeInformation.getType();
            if (conversionService.canConvert(byte[].class, typeToUse)) {
                target.add(fromBytes(elementData.get(key), typeToUse));
            } else {
                target.add(readInternal(key, typeToUse, new RedisRawData(elementData)));
            }
        }

        return isArray ? toArray(target, collectionType, valueType) : (target.isEmpty() ? null : target);
    }


    private void writeMap(@Nullable String keyspace, String path, Class<?> mapValueType, Map<?, ?> source,
                          RedisRawData sink) {

        if (CollectionUtils.isEmpty(source)) {
            return;
        }

        for (Map.Entry<?, ?> entry : source.entrySet()) {

            if (entry.getValue() == null || entry.getKey() == null) {
                continue;
            }

            String currentPath = path + ".[" + mapMapKey(entry.getKey()) + "]";

            if (!ClassUtils.isAssignable(mapValueType, entry.getValue().getClass())) {
                throw new MappingException(
                        String.format(INVALID_TYPE_ASSIGNMENT, entry.getValue().getClass(), currentPath, mapValueType));
            }

            if (customConversions.hasCustomWriteTarget(entry.getValue().getClass())) {
                writeToBucket(currentPath, entry.getValue(), sink, mapValueType);
            } else {
                writeInternal(keyspace, currentPath, entry.getValue(), TypeInformation.of(mapValueType), sink);
            }
        }
    }

    private String mapMapKey(Object key) {

        if (conversionService.canConvert(key.getClass(), byte[].class)) {
            return Strings.toString(conversionService.convert(key, byte[].class));
        }

        return conversionService.convert(key, String.class);
    }


    @Nullable
    private Map<?, ?> readMapOfSimpleTypes(String path, Class<?> mapType, Class<?> keyType, Class<?> valueType,
                                           RedisRawData source) {

        Bucket partial = source.getBucket().extract(path + ".[");

        Map<Object, Object> target = CollectionFactory.createMap(mapType, partial.size());

        for (Map.Entry<String, byte[]> entry : partial.entrySet()) {

            if (typeMapper.isTypeKey(entry.getKey())) {
                continue;
            }

            Object key = extractMapKeyForPath(path, entry.getKey(), keyType);
            Class<?> typeToUse = getTypeHint(path + ".[" + key + "]", source.getBucket(), valueType);
            target.put(key, fromBytes(entry.getValue(), typeToUse));
        }

        return target.isEmpty() ? null : target;
    }


    @Nullable
    private Map<?, ?> readMapOfComplexTypes(String path, Class<?> mapType, Class<?> keyType, Class<?> valueType,
                                            RedisRawData source) {

        Set<String> keys = source.getBucket().extractAllKeysFor(path);

        Map<Object, Object> target = CollectionFactory.createMap(mapType, keys.size());

        for (String key : keys) {

            Bucket partial = source.getBucket().extract(key);

            Object mapKey = extractMapKeyForPath(path, key, keyType);

            TypeInformation<?> typeInformation = typeMapper.readType(source.getBucket().getPropertyPath(key),
                    TypeInformation.of(valueType));

            Object o = readInternal(key, typeInformation.getType(), new RedisRawData(partial));
            target.put(mapKey, o);
        }

        return target.isEmpty() ? null : target;
    }

    @Nullable
    private Object extractMapKeyForPath(String path, String key, Class<?> targetType) {
        // \.\[)(.*?)(\])
        String regex = "^(" + Pattern.quote(path) + "\\.\\[)(.*?)(])";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(key);
        if (!matcher.find()) {
            throw new IllegalArgumentException(
                    String.format("Cannot extract map value for key '%s' in path '%s'.", key, path));
        }

        Object mapKey = matcher.group(2);

        if (ClassUtils.isAssignable(targetType, mapKey.getClass())) {
            return mapKey;
        }

        return conversionService.convert(toBytes(mapKey), targetType);
    }

    private Class<?> getTypeHint(String path, Bucket bucket, Class<?> fallback) {

        TypeInformation<?> typeInformation = typeMapper.readType(bucket.getPropertyPath(path),
                TypeInformation.of(fallback));
        return typeInformation.getType();
    }

    /**
     * Convert given source to binary representation using the underlying {@link ConversionService}.
     */
    public byte[] toBytes(Object source) {

        if (source instanceof byte[] bytes) {
            return bytes;
        }

        return conversionService.convert(source, byte[].class);
    }

    /**
     * Convert given binary representation to desired target type using the underlying {@link ConversionService}.
     */
    public <T> T fromBytes(byte[] source, Class<T> type) {

        if (type.isInstance(source)) {
            return type.cast(source);
        }

        return conversionService.convert(source, type);
    }

    /**
     * Converts a given {@link Collection} into an array considering primitive types.
     *
     * @param source    {@link Collection} of values to be added to the array.
     * @param arrayType {@link Class} of array.
     * @param valueType to be used for conversion before setting the actual value.
     */
    @Nullable
    private Object toArray(Collection<Object> source, Class<?> arrayType, Class<?> valueType) {

        if (source.isEmpty()) {
            return null;
        }

        if (!ClassUtils.isPrimitiveArray(arrayType)) {
            return source.toArray((Object[]) Array.newInstance(valueType, source.size()));
        }

        Object targetArray = Array.newInstance(valueType, source.size());
        Iterator<Object> iterator = source.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Array.set(targetArray, i, conversionService.convert(iterator.next(), valueType));
            i++;
        }
        return i > 0 ? targetArray : null;
    }

    /**
     * Set {@link CustomConversions} to be applied.
     */
    public void setCustomConversions(@Nullable CustomConversions customConversions) {
        this.customConversions = customConversions != null ? customConversions : new RedisCustomConversions();
    }

    @Override
    public @NonNull RedisMappingContext getMappingContext() {
        return this.mappingContext;
    }

    @Nullable

    public IndexResolver getIndexResolver() {
        return this.indexResolver;
    }


    @Override
    public @NonNull ConversionService getConversionService() {
        return this.conversionService;
    }

    public void afterPropertiesSet() {
        this.initializeConverters();
    }

    private void initializeConverters() {
        customConversions.registerConvertersIn(conversionService);
    }

    private static boolean isByteArray(RedisPersistentProperty property) {
        return property.getType().equals(byte[].class);
    }

    private static boolean isNotByteArray(RedisPersistentProperty property) {
        return !property.getType().equals(byte[].class);
    }

    private static boolean isByteArray(TypeInformation<?> type) {
        return type.getType().equals(byte[].class);
    }

    /**
     * @author Christoph Strobl
     * @author Mark Paluch
     */
    private class ConverterAwareParameterValueProvider implements PropertyValueProvider<RedisPersistentProperty> {

        private final String path;
        private final RedisRawData source;
        private final ConversionService conversionService;

        ConverterAwareParameterValueProvider(String path, RedisRawData source, ConversionService conversionService) {

            this.path = path;
            this.source = source;
            this.conversionService = conversionService;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T getPropertyValue(RedisPersistentProperty property) {

            Object value = readProperty(path, source, property);

            if (value == null || ClassUtils.isAssignableValue(property.getType(), value)) {
                return (T) value;
            }

            return (T) conversionService.convert(value, property.getType());
        }
    }

    private enum NaturalOrderingKeyComparator implements Comparator<String> {

        INSTANCE;

        public int compare(String s1, String s2) {

            int s1offset = 0;
            int s2offset = 0;

            while (s1offset < s1.length() && s2offset < s2.length()) {

                BoostMappingRedisConverter.NaturalOrderingKeyComparator.Part thisPart = extractPart(s1, s1offset);
                BoostMappingRedisConverter.NaturalOrderingKeyComparator.Part thatPart = extractPart(s2, s2offset);

                int result = thisPart.compareTo(thatPart);

                if (result != 0) {
                    return result;
                }

                s1offset += thisPart.length();
                s2offset += thatPart.length();
            }

            return 0;
        }

        private BoostMappingRedisConverter.NaturalOrderingKeyComparator.Part extractPart(String source, int offset) {

            StringBuilder builder = new StringBuilder();

            char c = source.charAt(offset);
            builder.append(c);

            boolean isDigit = Character.isDigit(c);
            for (int i = offset + 1; i < source.length(); i++) {

                c = source.charAt(i);
                if ((isDigit && !Character.isDigit(c)) || (!isDigit && Character.isDigit(c))) {
                    break;
                }
                builder.append(c);
            }

            return new BoostMappingRedisConverter.NaturalOrderingKeyComparator.Part(builder.toString(), isDigit);
        }

        private static class Part implements Comparable<BoostMappingRedisConverter.NaturalOrderingKeyComparator.Part> {

            private final String rawValue;
            private final @Nullable Long longValue;

            Part(String value, boolean isDigit) {

                this.rawValue = value;
                this.longValue = isDigit ? Long.valueOf(value) : null;
            }

            boolean isNumeric() {
                return longValue != null;
            }

            int length() {
                return rawValue.length();
            }

            @Override
            public int compareTo(@NonNull BoostMappingRedisConverter.NaturalOrderingKeyComparator.Part that) {

                if (this.isNumeric() && that.isNumeric()) {
                    return this.longValue.compareTo(that.longValue);
                }

                return this.rawValue.compareTo(that.rawValue);
            }
        }
    }

    /**
     * Value object representing a Redis Hash/Object identifier composed from keyspace and object id in the form of
     * {@literal keyspace:id}.
     *
     * @author Mark Paluch
     * @author Stefan Berger
     * @since 1.8.10
     */
    @Getter
    public static class KeyspaceIdentifier {

        public static final String PHANTOM = "phantom";
        public static final String DELIMITER = ":";
        public static final String PHANTOM_SUFFIX = DELIMITER + PHANTOM;

        private final String keyspace;
        private final String id;
        private final boolean phantomKey;

        private KeyspaceIdentifier(String keyspace, String id, boolean phantomKey) {

            this.keyspace = keyspace;
            this.id = id;
            this.phantomKey = phantomKey;
        }

        /**
         * Parse a {@code key} into {@link BoostMappingRedisConverter.KeyspaceIdentifier}.
         *
         * @param key the key representation.
         * @return {@link BoostMappingRedisConverter.BinaryKeyspaceIdentifier} for binary key.
         */
        public static BoostMappingRedisConverter.KeyspaceIdentifier of(String key) {

            Assert.isTrue(isValid(key), String.format("Invalid key %s", key));

            boolean phantomKey = key.endsWith(PHANTOM_SUFFIX);
            int keyspaceEndIndex = key.indexOf(DELIMITER);
            String keyspace = key.substring(0, keyspaceEndIndex);
            String id;

            if (phantomKey) {
                id = key.substring(keyspaceEndIndex + 1, key.length() - PHANTOM_SUFFIX.length());
            } else {
                id = key.substring(keyspaceEndIndex + 1);
            }

            return new BoostMappingRedisConverter.KeyspaceIdentifier(keyspace, id, phantomKey);
        }

        /**
         * Check whether the {@code key} is valid, in particular whether the key contains a keyspace and an id part in the
         * form of {@literal keyspace:id}.
         *
         * @param key the key.
         * @return {@literal true} if the key is valid.
         */
        public static boolean isValid(@Nullable String key) {

            if (key == null) {
                return false;
            }

            int keyspaceEndIndex = key.indexOf(DELIMITER);

            return keyspaceEndIndex > 0;
        }

    }

    /**
     * Value object representing a binary Redis Hash/Object identifier composed from keyspace and object id in the form of
     * {@literal keyspace:id}.
     *
     * @author Mark Paluch
     * @author Stefan Berger
     * @since 1.8.10
     */
    @Getter
    public static class BinaryKeyspaceIdentifier {

        public static final byte[] PHANTOM = BoostMappingRedisConverter.KeyspaceIdentifier.PHANTOM.getBytes();
        public static final byte DELIMITER = ':';
        public static final byte[] PHANTOM_SUFFIX = ByteUtils.concat(new byte[]{DELIMITER}, PHANTOM);

        private final byte[] keyspace;
        private final byte[] id;
        private final boolean phantomKey;

        private BinaryKeyspaceIdentifier(byte[] keyspace, byte[] id, boolean phantomKey) {

            this.keyspace = keyspace;
            this.id = id;
            this.phantomKey = phantomKey;
        }

        /**
         * Parse a binary {@code key} into {@link BoostMappingRedisConverter.BinaryKeyspaceIdentifier}.
         *
         * @param key the binary key representation.
         * @return {@link BoostMappingRedisConverter.BinaryKeyspaceIdentifier} for binary key.
         */
        public static BoostMappingRedisConverter.BinaryKeyspaceIdentifier of(byte[] key) {

            Assert.isTrue(isValid(key), String.format("Invalid key %s", new String(key)));

            boolean phantomKey = ByteUtils.startsWith(key, PHANTOM_SUFFIX, key.length - PHANTOM_SUFFIX.length);

            int keyspaceEndIndex = ByteUtils.indexOf(key, DELIMITER);
            byte[] keyspace = extractKeyspace(key, keyspaceEndIndex);
            byte[] id = extractId(key, phantomKey, keyspaceEndIndex);

            return new BoostMappingRedisConverter.BinaryKeyspaceIdentifier(keyspace, id, phantomKey);
        }

        /**
         * Check whether the {@code key} is valid, in particular whether the key contains a keyspace and an id part in the
         * form of {@literal keyspace:id}.
         *
         * @param key the key.
         * @return {@literal true} if the key is valid.
         */
        public static boolean isValid(byte[] key) {

            if (key.length == 0) {
                return false;
            }

            int keyspaceEndIndex = ByteUtils.indexOf(key, DELIMITER);

            return keyspaceEndIndex > 0 && key.length > keyspaceEndIndex;
        }

        private static byte[] extractId(byte[] key, boolean phantomKey, int keyspaceEndIndex) {

            int idSize;

            if (phantomKey) {
                idSize = (key.length - PHANTOM_SUFFIX.length) - (keyspaceEndIndex + 1);
            } else {

                idSize = key.length - (keyspaceEndIndex + 1);
            }

            byte[] id = new byte[idSize];
            System.arraycopy(key, keyspaceEndIndex + 1, id, 0, idSize);

            return id;
        }

        private static byte[] extractKeyspace(byte[] key, int keyspaceEndIndex) {

            byte[] keyspace = new byte[keyspaceEndIndex];
            System.arraycopy(key, 0, keyspace, 0, keyspaceEndIndex);

            return keyspace;
        }

    }
}
