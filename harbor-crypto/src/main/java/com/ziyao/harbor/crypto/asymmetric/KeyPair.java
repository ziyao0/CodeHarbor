package com.ziyao.harbor.crypto.asymmetric;

import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.crypto.AbstractAlgorithm;
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
    /**
     * 公钥.
     */
    private final byte[] pubKey;

    /**
     * 私钥.
     */
    private final byte[] priKey;


    public KeyPair(String algorithm, byte[] pubKey, byte[] priKey) {
        super(algorithm);
        this.priKey = priKey;
        this.pubKey = pubKey;
        this.publicKey = HexUtils.encodeHexStr(pubKey);
        this.privateKey = HexUtils.encodeHexStr(priKey);
    }
}
