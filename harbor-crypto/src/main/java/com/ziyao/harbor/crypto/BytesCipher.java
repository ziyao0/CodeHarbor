package com.ziyao.harbor.crypto;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public interface BytesCipher extends Algorithm {

    /**
     * 对给定的秘文进行解密并转换为字符串类型
     *
     * @param encrypt 密文
     * @return 返回解密后的数据
     */
    byte[] decrypt(byte[] encrypt);

    /**
     * 对原始明文进行加密
     *
     * @param input 带加密的字符串
     * @return 返回密文
     */
    byte[] encrypt(byte[] input);
}
