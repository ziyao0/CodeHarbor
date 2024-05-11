package com.ziyao.security.generator;

/**
 * @author ziyao
 * @since 2024/05/06 10:41:06
 */
public class SM2PairGenerator implements KeyPairGenerator {


    @Override
    public KeyPair generateKeyPair(KeyPairContext context) {
        return null;
    }

    @Override
    public KeyPairType type() {
        return KeyPairType.SM2;
    }
}
