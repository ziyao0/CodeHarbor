package com.ziyao.harbor.crypto;

/**
 * 加密器
 *
 * @author ziyao zhang
 * @since 2023/10/18
 */
public interface Encryptor {

    String encrypt(CharSequence plaintext);
}
