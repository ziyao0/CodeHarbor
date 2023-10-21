package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.utils.HexUtils;
import lombok.Getter;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
@Getter
public class KeyPair extends AbstractAlgorithm {

    /**
     * 公钥.
     */
    private final String publicKey;

    /**
     * 私钥.
     */
    private final String privateKey;


    public KeyPair(String algorithm, byte[] publicKey, byte[] privateKey) {
        super(algorithm);
        this.publicKey = HexUtils.encodeHexStr(publicKey);
        this.privateKey = HexUtils.encodeHexStr(privateKey);
    }
}
