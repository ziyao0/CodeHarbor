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
public class RedisData2 extends RedisData {

    private byte[] raw;

    public RedisData2(Map<byte[], byte[]> raw) {
        super(Bucket.newBucketFromRawMap(raw));
    }

    public RedisData2(byte[] raw) {
        this.raw = raw;
    }


    public RedisData2() {

    }

    public RedisData2(Bucket bucket) {
        super(bucket);
    }

    public RedisData2 create(Map<byte[], byte[]> raw) {
        RedisData2 redisData2 = new RedisData2(raw);
        redisData2.setId(getId());
        redisData2.setKeyspace(getKeyspace());
        if (getTimeToLive() != null) {
            redisData2.setTimeToLive(getTimeToLive());
        }
        return redisData2;
    }

}
