package com.ziyao.harbor.crypto.symmetric;

import com.ziyao.harbor.core.codec.Base64;
import com.ziyao.harbor.core.utils.Charsets;
import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.core.utils.Strings;

import java.nio.charset.Charset;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public interface SymmetricEncryptor {

    /**
     * 加密
     *
     * @param data 被加密的bytes
     * @return 加密后的bytes
     */
    byte[] encrypt(byte[] data);


    /**
     * 加密
     *
     * @param data 数据
     * @return 加密后的Hex
     */
    default String encryptHex(byte[] data) {
        return HexUtils.encodeHexStr(encrypt(data));
    }

    /**
     * 加密
     *
     * @param data 数据
     * @return 加密后的Base64
     */
    default String encryptBase64(byte[] data) {
        return Base64.encode(encrypt(data));
    }

    /**
     * 加密
     *
     * @param data    被加密的字符串
     * @param charset 编码
     * @return 加密后的bytes
     */
    default byte[] encrypt(String data, String charset) {
        return encrypt(Strings.toBytes(data, charset));
    }

    /**
     * 加密
     *
     * @param data    被加密的字符串
     * @param charset 编码
     * @return 加密后的bytes
     */
    default byte[] encrypt(String data, Charset charset) {
        return encrypt(Strings.toBytes(data, charset));
    }

    /**
     * 加密
     *
     * @param data    被加密的字符串
     * @param charset 编码
     * @return 加密后的Hex
     */
    default String encryptHex(String data, String charset) {
        return HexUtils.encodeHexStr(encrypt(data, charset));
    }

    /**
     * 加密
     *
     * @param data    被加密的字符串
     * @param charset 编码
     * @return 加密后的Hex
     */
    default String encryptHex(String data, Charset charset) {
        return HexUtils.encodeHexStr(encrypt(data, charset));
    }

    /**
     * 加密
     *
     * @param data    被加密的字符串
     * @param charset 编码
     * @return 加密后的Base64
     */
    default String encryptBase64(String data, String charset) {
        return Base64.encode(encrypt(data, charset));
    }

    /**
     * 加密
     *
     * @param data    被加密的字符串
     * @param charset 编码
     * @return 加密后的Base64
     */
    default String encryptBase64(String data, Charset charset) {
        return Base64.encode(encrypt(data, charset));
    }

    /**
     * 加密，使用UTF-8编码
     *
     * @param data 被加密的字符串
     * @return 加密后的bytes
     */
    default byte[] encrypt(String data) {
        return encrypt(Strings.toBytes(data, Charsets.CHARSET_UTF_8));
    }

    /**
     * 加密，使用UTF-8编码
     *
     * @param data 被加密的字符串
     * @return 加密后的Hex
     */
    default String encryptHex(String data) {
        return HexUtils.encodeHexStr(encrypt(data));
    }

    /**
     * 加密，使用UTF-8编码
     *
     * @param data 被加密的字符串
     * @return 加密后的Base64
     */
    default String encryptBase64(String data) {
        return Base64.encode(encrypt(data));
    }

}