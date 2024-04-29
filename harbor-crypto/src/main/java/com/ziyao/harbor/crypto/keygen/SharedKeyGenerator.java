package com.ziyao.harbor.crypto.keygen;

/**
 * @author ziyao zhang
 * @since 2024/3/26
 */
public class SharedKeyGenerator implements BytesKeyGenerator {

    private final byte[] sharedKey;

    SharedKeyGenerator(byte[] sharedKey) {
        this.sharedKey = sharedKey;
    }

    @Override
    public int getKeyLength() {
        return this.sharedKey.length;
    }

    @Override
    public byte[] generateKey() {
        return this.sharedKey;
    }

}
