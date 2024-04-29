package com.ziyao.harbor.crypto.keygen;

import java.security.SecureRandom;

/**
 * @author ziyao zhang
 * @since 2024/3/26
 */
public class SecureRandomBytesKeyGenerator implements BytesKeyGenerator {

    private static final int DEFAULT_KEY_LENGTH = 8;

    private final SecureRandom random;

    private final int keyLength;

    /**
     * Creates a secure random key generator using the defaults.
     */
    SecureRandomBytesKeyGenerator() {
        this(DEFAULT_KEY_LENGTH);
    }

    /**
     * Creates a secure random key generator with a custom key length.
     */
    SecureRandomBytesKeyGenerator(int keyLength) {
        this.random = new SecureRandom();
        this.keyLength = keyLength;
    }

    @Override
    public int getKeyLength() {
        return this.keyLength;
    }

    @Override
    public byte[] generateKey() {
        byte[] bytes = new byte[this.keyLength];
        this.random.nextBytes(bytes);
        return bytes;
    }

}