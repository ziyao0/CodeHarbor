package com.ziyao.harbor.crypto.generator;

/**
 * @author ziyao
 * @since 2024/05/06 11:19:39
 */
public abstract class KeyPairGenerators {

    private static final KeyPairGenerator GENERATOR;

    static {
        GENERATOR = new DelegatingKeyPairGenerator(
                new SM2PairGenerator(),
                new SM4PairGenerator()
        );
    }

    public static KeyPairGenerator getGenerator() {
        return GENERATOR;
    }

    public static KeyPair generateKeyPair(KeyPairContext context) {
        return GENERATOR.generateKeyPair(context);
    }
}
