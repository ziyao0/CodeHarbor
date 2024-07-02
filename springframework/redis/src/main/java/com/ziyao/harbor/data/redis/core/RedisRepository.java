package com.ziyao.harbor.data.redis.core;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @time 2024/6/29
 */
public interface RedisRepository<T, ID> extends Repository {

    /**
     * 判断缓存中有没有这个key
     */
    boolean hasKey(ID id);

    /**
     * 删除key
     */
    void delete(ID id);

    /**
     * 刷新过期时间
     */
    void expire(ID id, long timeout, TimeUnit timeUnit);

}
