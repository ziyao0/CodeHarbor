package com.ziyao.harbor.data.redis.core;

import java.lang.annotation.*;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Serializer {

    /**
     * 返回redis key 类型
     */
    Class<?> keyType() default String.class;

    /**
     * 返回redis value 类型
     */
    Class<?> valueType() default Object.class;

    /**
     * 返回redis 哈希 key 类型
     */
    Class<?> hkType() default Object.class;

    /**
     * 返回redis 哈希 value 类型
     */
    Class<?> hvType() default Object.class;
}
