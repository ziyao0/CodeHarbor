package com.ziyao.harbor.data.redis.core;

import java.lang.annotation.*;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisKey {
    // redis key
    String value();
}
