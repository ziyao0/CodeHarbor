package com.ziyao.harbor.data.redis.core.convert;

import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ziyao zhang
 * @time 2024/7/2
 */
public class Bucket2 {

    public static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final Comparator<String> COMPARATOR = Comparator.nullsFirst(Comparator.naturalOrder());

    private final NavigableMap<String, Object> data = new TreeMap<>(COMPARATOR);

    public Bucket2() {
    }

    Bucket2(Map<String, Object> data) {

        Assert.notNull(data, "Initial data must not be null");
        this.data.putAll(data);
    }

    public void put(String path, @Nullable byte[] value) {

        Assert.hasText(path, "Path to property must not be null or empty");
        data.put(path, value);
    }

    public void remove(String path) {

        Assert.hasText(path, "Path to property must not be null or empty");
        data.remove(path);
    }

    @Nullable
    public Object get(String path) {

        Assert.hasText(path, "Path to property must not be null or empty");
        return data.get(path);
    }

    public boolean hasValue(String path) {
        return get(path) != null;
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        return data.entrySet();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public int size() {
        return data.size();
    }

    public Collection<Object> values() {
        return data.values();
    }

    public Set<String> keySet() {
        return data.keySet();
    }

    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(this.data);
    }

    public static Bucket2 newBucketFromRawMap(Map<String, Object> source) {
        return new Bucket2(source);
    }

    public Bucket2 extract(String prefix) {
        return new Bucket2(data.subMap(prefix, prefix + Character.MAX_VALUE));
    }

    public Set<String> extractAllKeysFor(String path) {

        if (!StringUtils.hasText(path)) {
            return keySet();
        }

        Pattern pattern = Pattern.compile("^(" + Pattern.quote(path) + ")\\.\\[.*?\\]");

        Set<String> keys = new LinkedHashSet<>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {

            Matcher matcher = pattern.matcher(entry.getKey());
            if (matcher.find()) {
                keys.add(matcher.group());
            }
        }

        return keys;
    }

    public Map<byte[], byte[]> rawMap() {

        Map<byte[], byte[]> raw = new LinkedHashMap<>(data.size());
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getValue() != null) {
                raw.put(entry.getKey().getBytes(CHARSET), null);
            }
        }
        return raw;
    }

    public BucketPropertyPath getPath() {
        return BucketPropertyPath.from(this);
    }

    public BucketPropertyPath getPropertyPath(String property) {
        return BucketPropertyPath.from(this, property);
    }


    public static class BucketPropertyPath {

        @Getter
        private final Bucket2 bucket;
        private final @Nullable String prefix;

        private BucketPropertyPath(Bucket2 bucket, String prefix) {

            Assert.notNull(bucket, "Bucket must not be null");

            this.bucket = bucket;
            this.prefix = prefix;
        }

        public static BucketPropertyPath from(Bucket2 bucket) {
            return new BucketPropertyPath(bucket, null);
        }

        public static BucketPropertyPath from(Bucket2 bucket, @Nullable String prefix) {
            return new BucketPropertyPath(bucket, prefix);
        }


        private String getPath(String key) {
            return StringUtils.hasText(prefix) ? prefix + "." + key : key;
        }

        @Nullable
        public String getPrefix() {
            return this.prefix;
        }
    }
}
