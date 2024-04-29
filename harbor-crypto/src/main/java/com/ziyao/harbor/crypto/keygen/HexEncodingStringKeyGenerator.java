package com.ziyao.harbor.crypto.keygen;

import com.ziyao.harbor.core.utils.HexUtils;

/**
 * @author ziyao zhang
 * @since 2024/3/26
 */
public class HexEncodingStringKeyGenerator implements StringKeyGenerator {

    private final BytesKeyGenerator keyGenerator;

    HexEncodingStringKeyGenerator(BytesKeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Override
    public String generateKey() {
        return new String(HexUtils.encodeHex(this.keyGenerator.generateKey()));
    }

}
