package com.ziyao.harbor.data.redis.core;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/3/1
 */
public interface CommonOperations {
    /**
     * 刷新过期时间
     */
    void refresh();

    /**
     * 刷新过期时间
     */
    void refresh(long timeout, TimeUnit unit);

    boolean deleteKey();
}
