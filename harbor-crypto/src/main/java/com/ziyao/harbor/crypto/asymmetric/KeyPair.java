package com.ziyao.harbor.crypto.asymmetric;

import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.AbstractAlgorithm;
import com.ziyao.harbor.crypto.Algorithm;
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
    private String publicKey;

    /**
     * 私钥.
     */
    private String privateKey;
    /**
     * 公钥.
     */
    private byte[] pubKey;

    /**
     * 私钥.
     */
    private byte[] priKey;


    public KeyPair(String algorithm, byte[] pubKey, byte[] priKey) {
        super(algorithm);
        this.priKey = priKey;
        this.pubKey = pubKey;
        this.publicKey = HexUtils.encodeHexStr(pubKey);
        this.privateKey = HexUtils.encodeHexStr(priKey);
    }

    public KeyPair() {
        super(Algorithm.SM2);
    }

    public static KeyPair merge(KeyPair k1, KeyPair k2) {
        KeyPair keyPair = new KeyPair();
        return null;
    }

    public void merge(KeyPair keyPair) {
        if (null == keyPair) {
            return;
        }
        String publicKey = keyPair.getPublicKey();
        String privateKey = keyPair.getPrivateKey();
        if (Strings.hasText(publicKey)) {
            this.publicKey = publicKey;
        }
        if (Strings.hasText(privateKey)) {
            this.privateKey = privateKey;
        }
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setPubKey(byte[] pubKey) {
        this.pubKey = pubKey;
    }

    public void setPriKey(byte[] priKey) {
        this.priKey = priKey;
    }
}
