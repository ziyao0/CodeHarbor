package com.ziyao.harbor.data.redis.core;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @time 2024/6/29
 */
public interface RedisRepository<ID> extends Repository {

    /**
     * 判断缓存中有没有这个key
     */
    boolean hasKey(ID key);

    /**
     * 删除key
     */
    boolean delete(ID key);

    /**
     * 刷新过期时间
     */
    void timeToLive(ID key, long timeout, TimeUnit timeUnit);

}
