package com.ziyao.harbor.core.codec;


import com.ziyao.harbor.core.utils.Charsets;
import com.ziyao.harbor.core.utils.Strings;

import java.nio.charset.Charset;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class Base64 {
    private static final Charset DEFAULT_CHARSET = Charsets.CHARSET_UTF_8;

    /**
     * 编码为Base64，非URL安全的
     *
     * @param arr     被编码的数组
     * @param lineSep 在76个char之后是CRLF还是EOF
     * @return 编码后的bytes
     */
    public static byte[] encode(byte[] arr, boolean lineSep) {
        return lineSep ?
                java.util.Base64.getMimeEncoder().encode(arr) :
                java.util.Base64.getEncoder().encode(arr);
    }

    /**
     * 编码为Base64，URL安全的
     *
     * @param arr     被编码的数组
     * @param lineSep 在76个char之后是CRLF还是EOF
     * @return 编码后的bytes
     * @deprecated 按照RFC2045规范，URL安全的Base64无需换行
     */
    @Deprecated
    public static byte[] encodeUrlSafe(byte[] arr, boolean lineSep) {
        return Base64Encoder.encodeUrlSafe(arr, lineSep);
    }

    /**
     * base64编码
     *
     * @param source 被编码的base64字符串
     * @return 被加密后的字符串
     */
    public static String encode(CharSequence source) {
        return encode(source, DEFAULT_CHARSET);
    }

    /**
     * base64编码，URL安全
     *
     * @param source 被编码的base64字符串
     * @return 被加密后的字符串
     */
    public static String encodeUrlSafe(CharSequence source) {
        return encodeUrlSafe(source, DEFAULT_CHARSET);
    }

    /**
     * base64编码
     *
     * @param source  被编码的base64字符串
     * @param charset 字符集
     * @return 被加密后的字符串
     */
    public static String encode(CharSequence source, String charset) {
        return encode(source, Charsets.forName(charset));
    }

    /**
     * base64编码，不进行padding(末尾不会填充'=')
     *
     * @param source  被编码的base64字符串
     * @param charset 编码
     * @return 被加密后的字符串
     */
    public static String encodeWithoutPadding(CharSequence source, String charset) {
        return encodeWithoutPadding(Strings.toBytes(source, charset));
    }

    /**
     * base64编码,URL安全
     *
     * @param source  被编码的base64字符串
     * @param charset 字符集
     * @return 被加密后的字符串
     * @deprecated 请使用 {@link #encodeUrlSafe(CharSequence, Charset)}
     */
    @Deprecated
    public static String encodeUrlSafe(CharSequence source, String charset) {
        return encodeUrlSafe(source, Charsets.forName(charset));
    }

    /**
     * base64编码
     *
     * @param source  被编码的base64字符串
     * @param charset 字符集
     * @return 被编码后的字符串
     */
    public static String encode(CharSequence source, Charset charset) {
        return encode(Strings.toBytes(source, charset));
    }

    /**
     * base64编码，URL安全的
     *
     * @param source  被编码的base64字符串
     * @param charset 字符集
     * @return 被加密后的字符串
     */
    public static String encodeUrlSafe(CharSequence source, Charset charset) {
        return encodeUrlSafe(Strings.toBytes(source, charset));
    }

    /**
     * base64编码
     *
     * @param source 被编码的base64字符串
     * @return 被加密后的字符串
     */
    public static String encode(byte[] source) {
        return java.util.Base64.getEncoder().encodeToString(source);
    }

    /**
     * base64编码，不进行padding(末尾不会填充'=')
     *
     * @param source 被编码的base64字符串
     * @return 被加密后的字符串
     */
    public static String encodeWithoutPadding(byte[] source) {
        return java.util.Base64.getEncoder().withoutPadding().encodeToString(source);
    }

    /**
     * base64编码,URL安全的
     *
     * @param source 被编码的base64字符串
     * @return 被加密后的字符串
     */
    public static String encodeUrlSafe(byte[] source) {
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(source);
    }


    /**
     * 编码为Base64<br>
     * 如果isMultiLine为{@code true}，则每76个字符一个换行符，否则在一行显示
     *
     * @param arr         被编码的数组
     * @param isMultiLine 在76个char之后是CRLF还是EOF
     * @param isUrlSafe   是否使用URL安全字符，一般为{@code false}
     * @return 编码后的bytes
     */
    public static byte[] encode(byte[] arr, boolean isMultiLine, boolean isUrlSafe) {
        return Base64Encoder.encode(arr, isMultiLine, isUrlSafe);
    }

    /**
     * base64解码
     *
     * @param source 被解码的base64字符串
     * @return 被加密后的字符串
     */
    public static String decodeStrGbk(CharSequence source) {
        return Base64Decoder.decodeStr(source, Charsets.CHARSET_GBK);
    }

    /**
     * base64解码
     *
     * @param source 被解码的base64字符串
     * @return 被加密后的字符串
     */
    public static String decodeStr(CharSequence source) {
        return Base64Decoder.decodeStr(source);
    }

    /**
     * base64解码
     *
     * @param source  被解码的base64字符串
     * @param charset 字符集
     * @return 被加密后的字符串
     */
    public static String decodeStr(CharSequence source, String charset) {
        return decodeStr(source, Charsets.forName(charset));
    }

    /**
     * base64解码
     *
     * @param source  被解码的base64字符串
     * @param charset 字符集
     * @return 被加密后的字符串
     */
    public static String decodeStr(CharSequence source, Charset charset) {
        return Base64Decoder.decodeStr(source, charset);
    }


    /**
     * base64解码
     *
     * @param base64 被解码的base64字符串
     * @return 解码后的bytes
     */
    public static byte[] decode(CharSequence base64) {
        return Base64Decoder.decode(base64);
    }

    /**
     * 解码Base64
     *
     * @param in 输入
     * @return 解码后的bytes
     */
    public static byte[] decode(byte[] in) {
        return Base64Decoder.decode(in);
    }

    /**
     * 检查是否为Base64
     *
     * @param base64 Base64的bytes
     * @return 是否为Base64
     */
    public static boolean isBase64(CharSequence base64) {
        if (base64 == null || base64.length() < 2) {
            return false;
        }

        final byte[] bytes = Strings.utf8Bytes(base64);

        if (bytes.length != base64.length()) {
            // 如果长度不相等，说明存在双字节字符，肯定不是Base64，直接返回false
            return false;
        }

        return isBase64(bytes);
    }

    /**
     * 检查是否为Base64
     *
     * @param base64Bytes Base64的bytes
     * @return 是否为Base64
     */
    public static boolean isBase64(byte[] base64Bytes) {
        if (base64Bytes == null || base64Bytes.length < 3) {
            return false;
        }
        boolean hasPadding = false;
        for (byte base64Byte : base64Bytes) {
            if (hasPadding) {
                if ('=' != base64Byte) {
                    // 前一个字符是'='，则后边的字符都必须是'='，即'='只能都位于结尾
                    return false;
                }
            } else if ('=' == base64Byte) {
                // 发现'=' 标记之
                hasPadding = true;
            } else if (!(Base64Decoder.isBase64Code(base64Byte) || isWhiteSpace(base64Byte))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isWhiteSpace(byte byteToCheck) {
        return switch (byteToCheck) {
            case ' ', '\n', '\r', '\t' -> true;
            default -> false;
        };
    }
}
