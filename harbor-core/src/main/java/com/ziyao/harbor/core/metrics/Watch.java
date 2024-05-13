package com.ziyao.harbor.core.metrics;

import java.lang.annotation.*;

/**
 * @author ziyao zhang
 * @since 2023/10/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Watch {

    /**
     * 秒表名称
     *
     * @see StopWatch
     */
    String value();

}
