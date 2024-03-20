package com.ziyao.harbor.data.redis.lock;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyao zhang
 * @since 2024/3/13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {

    /**
     * 锁名称
     */
    String value();

    /**
     * 锁定规则
     */
    RuleMode rule() default RuleMode.AWAIT;

    /**
     * 获取锁等待时间
     */
    long waitTime() default -1;

    /**
     * 持有锁的时间
     */
    long leaseTime() default -1;

    /**
     * 时间单位，默认为毫秒
     */
    TimeUnit Unit() default TimeUnit.MILLISECONDS;

    /**
     * 如果跳过，则实现跳过逻辑
     */
    Class<? extends LockCallback> callback() default DefaultLockCallback.class;
}
