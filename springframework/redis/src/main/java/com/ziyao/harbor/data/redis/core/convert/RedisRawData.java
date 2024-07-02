package com.ziyao.harbor.data.redis.core.convert;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.convert.IndexedData;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @see <a href="https://blog.zziyao.cn">https://blog.zziyao.cn</a>
 * @since 2024/07/02 16:21:10
 */

public class RedisRawData {

    @Getter
    private final Bucket2 bucket;
    private final Set<IndexedData> indexedData;

    private @Nullable String keyspace;
    private @Nullable String id;
    @Getter
    @Setter
    private byte[] raw;
    /**
     * -- SETTER --
     * Set the time before expiration in
     */
    @Setter
    private @Nullable Long timeToLive;

    public RedisRawData() {
        this(Collections.emptyMap());
    }

    public RedisRawData(Map<String, Object> raw) {
        this(Bucket2.newBucketFromRawMap(raw));
    }

    public RedisRawData(Bucket2 bucket) {

        Assert.notNull(bucket, "Bucket must not be null");

        this.bucket = bucket;
        this.indexedData = new HashSet<>();
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    @Nullable
    public String getId() {
        return this.id;
    }

    @Nullable
    public Long getTimeToLive() {
        return timeToLive;
    }

    public void addIndexedData(IndexedData index) {

        Assert.notNull(index, "IndexedData to add must not be null");
        this.indexedData.add(index);
    }

    public void addIndexedData(Collection<IndexedData> indexes) {

        Assert.notNull(indexes, "IndexedData to add must not be null");
        this.indexedData.addAll(indexes);
    }

    public Set<IndexedData> getIndexedData() {
        return Collections.unmodifiableSet(this.indexedData);
    }

    @Nullable
    public String getKeyspace() {
        return keyspace;
    }

    public void setKeyspace(@Nullable String keyspace) {
        this.keyspace = keyspace;
    }


    /**
     * Set the time before expiration converting the given arguments to {@link TimeUnit#SECONDS}.
     *
     * @param timeToLive must not be {@literal null}
     * @param timeUnit   must not be {@literal null}
     */
    public void setTimeToLive(Long timeToLive, TimeUnit timeUnit) {

        Assert.notNull(timeToLive, "TimeToLive must not be null when used with TimeUnit");
        Assert.notNull(timeToLive, "TimeUnit must not be null");

        setTimeToLive(TimeUnit.SECONDS.convert(timeToLive, timeUnit));
    }

    @Override
    public String toString() {
        return "RedisDataObject [key=" + keyspace + ":" + id + ", hash=" + bucket + "]";
    }


}
