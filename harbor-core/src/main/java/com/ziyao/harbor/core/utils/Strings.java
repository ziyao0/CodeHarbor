package com.ziyao.harbor.core.utils;


import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.*;

/**
 * 字符串操作工具类
 *
 * @author ziyao zhang
 * @since 2023/7/31
 */
public abstract class Strings {

    private static final Charset default_charset = StandardCharsets.UTF_8;

    private Strings() {
    }

    /**
     * 检查给定的对象（可能是 {@code String}）是否为空。
     *
     * @param str 候选对象（可能是 {@code String}）
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * Check that the given {@code CharSequence} is neither {@code null} nor
     * of length 0.
     * <p>Note: this method returns {@code true} for a {@code CharSequence}
     * that purely consists of whitespace.
     * <p><pre class="code">
     * Strings.hasLength(null) = false
     * Strings.hasLength("") = false
     * Strings.hasLength(" ") = true
     * Strings.hasLength("Hello") = true
     * </pre>
     *
     * @param str the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null} and has length
     * @see #hasLength(String)
     * @see #hasText(CharSequence)
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && !str.isEmpty());
    }

    /**
     * Check that the given {@code String} is neither {@code null} nor of length 0.
     * <p>Note: this method returns {@code true} for a {@code String} that
     * purely consists of whitespace.
     *
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not {@code null} and has length
     * @see #hasLength(CharSequence)
     * @see #hasText(String)
     */
    public static boolean hasLength(String str) {
        return (str != null && !str.trim().isEmpty());
    }

    /**
     * Check whether the given {@code CharSequence} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code CharSequence} is not {@code null}, its length is greater than
     * 0, and it contains at least one non-whitespace character.
     * <p><pre class="code">
     * Strings.hasText(null) = false
     * Strings.hasText("") = false
     * Strings.hasText(" ") = false
     * Strings.hasText("12345") = true
     * Strings.hasText(" 12345 ") = true
     * </pre>
     *
     * @param str the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null},
     * its length is greater than 0, and it does not contain whitespace only
     * @see #hasText(String)
     * @see #hasLength(CharSequence)
     * @see Character#isWhitespace
     */
    public static boolean hasText(CharSequence str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    /**
     * Check whether the given {@code String} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code String} is not {@code null}, its length is greater than 0,
     * and it contains at least one non-whitespace character.
     *
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not {@code null}, its
     * length is greater than 0, and it does not contain whitespace only
     * @see #hasText(CharSequence)
     * @see #hasLength(String)
     * @see Character#isWhitespace
     */
    public static boolean hasText(String str) {
        return (str != null && !str.trim().isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Copy the given {@link Collection} into a {@code String} array.
     * <p>The {@code Collection} must contain {@code String} elements only.
     *
     * @param collection the {@code Collection} to copy
     *                   (potentially {@code null} or empty)
     * @return the resulting {@code String} array
     */
    public static String[] toStringArray(Collection<String> collection) {
        return (collection != null ? collection.toArray(new String[0]) : new String[0]);
    }

    /**
     * Copy the given {@link Enumeration} into a {@code String} array.
     * <p>The {@code Collection} must contain {@code String} elements only.
     *
     * @param enumeration the {@code Collection} to copy
     *                    (potentially {@code null} or empty)
     * @return the resulting {@code String} array
     */
    public static String[] toStringArray(Enumeration<String> enumeration) {
        return (enumeration != null ? toStringArray(Collections.list(enumeration)) : new String[0]);
    }

    /**
     * Convert a {@code Collection} into a delimited {@code String} (e.g., CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param coll the {@code Collection} to convert (potentially {@code null} or empty)
     * @return the delimited {@code String}
     */
    public static String collectionToCommaDelimitedString(Collection<?> coll) {
        return collectionToDelimitedString(coll, ",");
    }

    /**
     * Convert a {@code Collection} into a delimited {@code String} (e.g. CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param coll  the {@code Collection} to convert (potentially {@code null} or empty)
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    /**
     * Convert a {@link Collection} to a delimited {@code String} (e.g. CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param coll   the {@code Collection} to convert (potentially {@code null} or empty)
     * @param delim  the delimiter to use (typically a ",")
     * @param prefix the {@code String} to start each element with
     * @param suffix the {@code String} to end each element with
     * @return the delimited {@code String}
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix) {

        if (com.ziyao.harbor.core.utils.Collections.isEmpty(coll)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Iterator<?> it = coll.iterator();
        while (it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * 对给定的字符串进行url{@link StandardCharsets#UTF_8}编码，
     *
     * @param str 给定字符串
     * @return 返回编码后的值
     */
    public static String encodeURLUTF8(String str) {
        if (hasLength(str)) {
            return URLEncoder.encode(str, default_charset);
        }
        return null;
    }

    /**
     * toBytes
     *
     * @param value target
     * @return <code>byte[]</code>
     */
    public static byte[] toBytes(String value) {
        if (Strings.hasLength(value)) {
            return value.getBytes(default_charset);
        }
        return new byte[0];
    }

    /**
     * toBytes
     *
     * @param obj target
     * @return <code>byte[]</code>
     */
    public static byte[] toBytes(Object obj) {
        if (Objects.nonNull(obj)) {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = null;
            try {
                objOut = new ObjectOutputStream(byteOut);
                objOut.writeObject(obj);
                return byteOut.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new byte[0];
    }

    public static <T> T toObject(byte[] bytes) {
        if (bytes.length != 0) {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
            try {
                ObjectInputStream objIn = new ObjectInputStream(byteIn);
                return (T) objIn.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * 返回一个新的 {@code String}，该字符串由 {@code CharSequence 元素} 的副本与指定的 {@code 分隔符} 的副本连接在一起。
     *
     * @param delimiter 用于分隔生成的 {@code 字符串} 中的每个 { 元素} 的字符序列
     * @param elements  一个 {@code 可迭代}，它将将其 {@code 元素} 连接在一起。
     * @return 由 {@code 元素} 参数组成的新 {@code 字符串}
     */
    public static String join(CharSequence delimiter,
                              Iterable<? extends CharSequence> elements) {
        Assert.notNull(delimiter, "");
        Assert.notNull(elements, "");
        StringJoiner joiner = new StringJoiner(delimiter);
        for (CharSequence cs : elements) {
            joiner.add(cs);
        }
        return joiner.toString();
    }

    public boolean startsWith(String value, String prefix) {
        if (hasLength(value)) {
            return value.startsWith(prefix);
        }
        return false;
    }

    public boolean endsWith(String value, String suffix) {
        if (hasLength(value)) {
            return value.endsWith(suffix);
        }
        return false;
    }
}
