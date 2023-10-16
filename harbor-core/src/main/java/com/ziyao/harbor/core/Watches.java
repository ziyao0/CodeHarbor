package com.ziyao.harbor.core;

import java.lang.annotation.*;

/**
 * @author ziyao zhang
 * @since 2023/10/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Watches {

    /**
     * 秒表名称
     *
     * @see StopWatch
     */
    String value();

}
