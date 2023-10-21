package com.ziyao.harbor.crypto.symmetric;

import com.ziyao.harbor.core.utils.Charsets;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.utils.SecureUtils;

import java.nio.charset.Charset;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public interface SymmetricDecryptor {
    /**
     * 解密
     *
     * @param bytes 被解密的bytes
     * @return 解密后的bytes
     */
    byte[] decrypt(byte[] bytes);


    /**
     * 解密为字符串
     *
     * @param bytes   被解密的bytes
     * @param charset 解密后的charset
     * @return 解密后的String
     */
    default String decryptStr(byte[] bytes, Charset charset) {
        return Strings.toString(decrypt(bytes), charset);
    }

    /**
     * 解密为字符串，默认UTF-8编码
     *
     * @param bytes 被解密的bytes
     * @return 解密后的String
     */
    default String decryptStr(byte[] bytes) {
        return decryptStr(bytes, Charsets.CHARSET_UTF_8);
    }

    /**
     * 解密Hex（16进制）或Base64表示的字符串
     *
     * @param data 被解密的String，必须为16进制字符串或Base64表示形式
     * @return 解密后的bytes
     */
    default byte[] decrypt(String data) {
        return decrypt(SecureUtils.decode(data));
    }

    /**
     * 解密Hex（16进制）或Base64表示的字符串
     *
     * @param data    被解密的String
     * @param charset 解密后的charset
     * @return 解密后的String
     */
    default String decryptStr(String data, Charset charset) {
        return Strings.toString(decrypt(data), charset);
    }

    /**
     * 解密Hex（16进制）或Base64表示的字符串，默认UTF-8编码
     *
     * @param data 被解密的String
     * @return 解密后的String
     */
    default String decryptStr(String data) {
        return decryptStr(data, Charsets.CHARSET_UTF_8);
    }
}