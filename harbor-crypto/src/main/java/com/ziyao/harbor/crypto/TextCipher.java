package com.ziyao.harbor.crypto;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface TextCipher extends Algorithm {

    /**
     * 对给定的秘文进行解密并转换为字符串类型
     *
     * @return 返回解密后的数据
     */
    String decrypt(String ciphertext);

    /**
     * 对原始明文进行加密
     *
     * @return 返回密文
     */
    String encrypt(String plaintext);


    default String digest(String plaintext) {
        return null;
    }
}
