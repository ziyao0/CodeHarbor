package com.ziyao.security.generator;

import java.util.List;

/**
 * @author ziyao
 * @since 2024/05/06 11:13:58
 */
public class DelegatingKeyPairGenerator implements KeyPairGenerator {

    private final List<KeyPairGenerator> generators;

    public DelegatingKeyPairGenerator(KeyPairGenerator... generators) {
        this.generators = List.copyOf(List.of(generators));
    }

    @Override
    public KeyPair generateKeyPair(KeyPairContext context) {

        for (KeyPairGenerator generator : generators) {
            if (generator.type().equals(context.type())) {
                return generator.generateKeyPair(context);
            }
        }
        return null;
    }

    @Override
    public KeyPairType type() {
        return KeyPairType.DELEGATE;
    }
}
