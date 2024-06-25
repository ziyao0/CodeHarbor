package com.ziyao.harbor.data.redis.core;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/3/1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeToLive {

    /**
     * 过期时间
     */
    long ttl() default -1;

    /**
     * 时间单位
     *
     * @return {@link TimeUnit }
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
