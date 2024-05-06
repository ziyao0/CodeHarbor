package com.ziyao.harbor.crypto.generator;

import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.crypto.symmetric.Mode;
import com.ziyao.harbor.crypto.symmetric.Padding;

/**
 * @author ziyao
 * @since 2024/05/06 11:04:05
 */
public interface KeyPair {

    String getAlgorithm();

    byte[] getPublicKeyBytes();

    byte[] getPrivateKeyBytes();

    byte[] getKeyBytes();

    byte[] getIvBytes();

    default String getPublicKey() {
        return HexUtils.encodeHexStr(getPublicKeyBytes());
    }

    default String getPrivateKey() {
        return HexUtils.encodeHexStr(getPrivateKeyBytes());
    }

    default String getKey() {
        return HexUtils.encodeHexStr(getKeyBytes());
    }

    default String getIv() {
        return HexUtils.encodeHexStr(getIvBytes());
    }

    Mode mode();

    Padding padding();
}
