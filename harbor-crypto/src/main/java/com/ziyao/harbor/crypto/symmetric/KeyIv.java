package com.ziyao.harbor.crypto.symmetric;

import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.crypto.AbstractAlgorithm;
import lombok.Getter;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Getter
public class KeyIv extends AbstractAlgorithm {

    private final String key;

    private final String iv;

    private final byte[] keyBytes;
    private final byte[] ivBytes;

    public KeyIv(String algorithm, byte[] keyBytes, byte[] ivBytes) {
        super(algorithm);
        this.keyBytes = keyBytes;
        this.ivBytes = ivBytes;
        this.key = HexUtils.encodeHexStr(keyBytes);
        this.iv = HexUtils.encodeHexStr(ivBytes);
    }
}
