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
    String key() default "";

    /**
     * 如果value有值，优先使用value
     * 会通过传入的参数替换{}
     * <p>
     * 例如：
     * {@code RedisKey#format()}为{@code "key:{}"},传入的参数为count,
     * 则最终的key为{@code "key:count"}
     */

    String format() default "";
}
