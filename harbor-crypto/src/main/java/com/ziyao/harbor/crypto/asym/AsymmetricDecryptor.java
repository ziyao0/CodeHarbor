package com.ziyao.harbor.crypto.asym;

/**
 * 解密器  编解码器 非对称
 * Asymmetric
 *
 * @author ziyao zhang
 * @since 2023/10/18
 */
public interface AsymmetricDecryptor<T> {

    /**
     * 对给定的密文信息进行解密，解密秘钥由{@link T}提供
     *
     * @param ciphertext 密文信息
     * @param t          提供的秘钥信息
     * @return 返回解密后的信息
     */
    String decrypt(CharSequence ciphertext, T t);
}
