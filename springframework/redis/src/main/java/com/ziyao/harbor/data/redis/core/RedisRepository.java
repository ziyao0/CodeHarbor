package com.ziyao.harbor.data.redis.core;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @time 2024/6/29
 */
public interface RedisRepository<T> extends Repository {

    /**
     * 判断缓存中有没有这个key
     */
    boolean hasKey(Object id, String keyspace, Class<T> type);

    /**
     * 删除key
     */
    void delete(Object id, String keyspace, Class<T> type);

    /**
     * 刷新过期时间
     */
    void timeToLive(Object id, String keyspace, Class<T> type, long timeout, TimeUnit timeUnit);

}
