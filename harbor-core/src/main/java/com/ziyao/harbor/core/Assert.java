package com.ziyao.harbor.core;

import com.ziyao.harbor.core.utils.Strings;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * @author ziyao zhang
 * @since 2023/8/24
 */
public abstract class Assert {
    private Assert() {
    }

    /**
     * Assert that a string is not {@code null}.
     *
     * @param value   要检查的字符串
     * @param message 断言失败后返回的异常信息
     */
    public static void notNull(@Nullable String value, String message) {
        if (Strings.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an Object is not {@code null}.
     *
     * @param object  要断言的对象
     * @param message 断言失败后返回的异常信息
     */
    public static void notNull(@Nullable Object object, String message) {
        if (ObjectUtils.isEmpty(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a string is  {@code null}.
     *
     * @param value   要检查的字符串
     * @param message 断言失败后返回的异常信息
     */
    public static void isNull(@Nullable String value, String message) {
        if (Strings.hasLength(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an Object is  {@code null}.
     *
     * @param object  要断言的对象
     * @param message 断言失败后返回的异常信息
     */
    public static void isNull(@Nullable Object object, String message) {
        if (!Objects.isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言指定时间戳是否已过期.
     *
     * @param timestamp 时间戳
     * @param validity  时间戳有效时间
     */
    public static void isTimestampNotExpire(String timestamp, long validity) {
        if (Strings.isEmpty(timestamp)) {
            throw new IllegalArgumentException("缺少时间戳(timestamp)");
        }
        try {
            long timestampLong = Long.parseLong(timestamp);
            if (Math.abs(System.currentTimeMillis() - timestampLong) >= validity) {
                throw new IllegalStateException("时间戳已过期");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("时间戳格式错误");
        }
    }
}
