package com.ziyao.harbor.crypto.generator;

/**
 * @author ziyao
 * @since 2024/05/06 10:40:18
 */
public interface KeyPairGenerator {
    /**
     * 密钥生成器
     *
     * @return 返回密钥信息
     */
    KeyPair generateKeyPair(KeyPairContext context);

    /**
     * 密钥类型
     *
     * @return {@link KeyPairType}
     */
    KeyPairType type();
}