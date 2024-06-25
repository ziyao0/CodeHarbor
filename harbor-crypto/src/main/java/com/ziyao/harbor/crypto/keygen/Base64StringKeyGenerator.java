package com.ziyao.harbor.crypto.keygen;

import java.util.Base64;

/**
 * @author ziyao zhang
 * @since 2024/3/26
 */
public class Base64StringKeyGenerator implements StringKeyGenerator {
    private static final int DEFAULT_KEY_LENGTH = 32;
    private final BytesKeyGenerator keyGenerator;
    private final Base64.Encoder encoder;

    public Base64StringKeyGenerator() {
        this(32);
    }

    public Base64StringKeyGenerator(int keyLength) {
        this(Base64.getEncoder(), keyLength);
    }

    public Base64StringKeyGenerator(Base64.Encoder encoder) {
        this(encoder, 32);
    }

    public Base64StringKeyGenerator(Base64.Encoder encoder, int keyLength) {
        if (encoder == null) {
            throw new IllegalArgumentException("encode cannot be null");
        } else if (keyLength < 8) {
            throw new IllegalArgumentException("keyLength must be greater than or equal to32");
        } else {
            this.encoder = encoder;
            this.keyGenerator = KeyGenerators.secureRandom(keyLength);
        }
    }

    public String generateKey() {
        byte[] key = this.keyGenerator.generateKey();
        byte[] base64EncodedKey = this.encoder.encode(key);
        return new String(base64EncodedKey);
    }
}
