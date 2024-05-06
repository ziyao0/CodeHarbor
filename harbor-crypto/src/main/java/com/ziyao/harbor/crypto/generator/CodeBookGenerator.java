package com.ziyao.harbor.crypto.generator;

/**
 * @author ziyao
 * @since 2024/05/06 11:26:38
 */
public class CodeBookGenerator implements KeyPairGenerator {


    @Override
    public KeyPair generateKeyPair(KeyPairContext context) {
        return null;
    }

    @Override
    public KeyPairType type() {
        return KeyPairType.CODE_BOOK;
    }
}
