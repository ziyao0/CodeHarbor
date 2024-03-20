package com.ziyao.harbor.data.redis.lock;

/**
 * @author ziyao zhang
 * @since 2024/3/20
 */
public interface LockCallback {

    public static final String METHOD_NAME = "callback";

    Object callback();
}
