package com.ziyao.harbor.core.utils;

import com.ziyao.harbor.core.lang.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

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
    public static void notNull(String value, String message) {
        if (Strings.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a string is not {@code null}.
     *
     * @param value   要检查的字符串
     * @param message 断言失败后返回的异常信息
     */
    public static void notNull(CharSequence value, String message) {
        if (Strings.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> void notNull(T value) {
        if (value instanceof String && Strings.isEmpty(value)) {
                throw new IllegalArgumentException("传入不能为空");
            }

        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("传入不能为空");
        }
    }

    public static <T> void notNull(T value, RuntimeException exception) {
        if (value instanceof String && Strings.isEmpty(value)) {
                throw exception;
            }

        if (Objects.isNull(value)) {
            throw exception;
        }
    }

    /**
     * Assert that a Collection is not {@code null}.
     *
     * @param value   要检查的集合对象
     * @param message 断言失败后返回的异常信息
     */
    public static void notNull(Collection<?> value, String message) {
        if (Collections.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a map is not {@code null}.
     *
     * @param value   要检查的集合对象
     * @param message 断言失败后返回的异常信息
     */
    public static void notNull(Map<?, ?> value, String message) {
        if (Collections.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an Object is not {@code null}.
     *
     * @param object  要断言的对象
     * @param message 断言失败后返回的异常信息
     */
    public static void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言字符串是否为空
     *
     * @param text    给定字符串
     * @param message 断言失败后返回信息
     */
    public static void hasLength(String text, String message) {
        if (!Strings.hasLength(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a string is  {@code null}.
     *
     * @param value   要检查的字符串
     * @param message 断言失败后返回的异常信息
     */
    public static void isNull(String value, String message) {
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
    public static void isNull(Object object, String message) {
        if (Objects.nonNull(object)) {
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

    public static void isTrue(boolean value, String message) {
        if (!value) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an array contains no {@code null} elements.
     * <p>Note: Does not complain if the array is empty!
     * <pre class="code">Assert.noNullElements(array, "The array must contain non-null elements");</pre>
     *
     * @param array   the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     */
    public static void noNullElements(@Nullable Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code IllegalStateException}
     * if the expression evaluates to {@code false}.
     * <p>Call {@link #isTrue} if you wish to throw an {@code IllegalArgumentException}
     * on an assertion failure.
     * <pre class="code">Assert.state(id == null, "The id property must not already be initialized");</pre>
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws IllegalStateException if {@code expression} is {@code false}
     */
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code IllegalStateException}
     * if the expression evaluates to {@code false}.
     * <p>Call {@link #isTrue} if you wish to throw an {@code IllegalArgumentException}
     * on an assertion failure.
     * <pre class="code">
     * Assert.state(entity.getId() == null,
     *     () -&gt; "ID for entity " + entity.getName() + " must not already be initialized");
     * </pre>
     *
     * @param expression      a boolean expression
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws IllegalStateException if {@code expression} is {@code false}
     * @since 5.0
     */
    public static void state(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new IllegalStateException(nullSafeGet(messageSupplier));
        }
    }

    @Nullable
    private static String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }


    public static void hasText(@Nullable String text, String message) {
        if (!Strings.hasText(text)) {
            throw new IllegalArgumentException(message);
        }
    }
}
