package com.ziyao.harbor.data.redis.core.convert;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.convert.Bucket;
import org.springframework.data.redis.core.convert.RedisData;

import java.util.Map;

/**
 * @author ziyao
 * @see <a href="https://blog.zziyao.cn">https://blog.zziyao.cn</a>
 * @since 2024/07/02 16:21:10
 */
@Setter
@Getter
public class RedisRawData extends RedisData {

    private byte[] raw;

    public RedisRawData(Map<byte[], byte[]> raw) {
        super(Bucket.newBucketFromRawMap(raw));
    }

    public RedisRawData(byte[] raw) {
        this.raw = raw;
    }


    public RedisRawData() {

    }

    public RedisRawData(Bucket bucket) {
        super(bucket);
    }

    public RedisRawData create(Map<byte[], byte[]> raw) {
        RedisRawData redisRawData = new RedisRawData(raw);
        redisRawData.setId(getId());
        redisRawData.setKeyspace(getKeyspace());
        if (getTimeToLive() != null) {
            redisRawData.setTimeToLive(getTimeToLive());
        }
        return redisRawData;
    }

}
