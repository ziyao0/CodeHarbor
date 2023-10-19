package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.codec.Decoder;
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


    public KeyPair(String algorithm, Decoder<CharSequence, byte[]> decoder, String publicKey, String privateKey) {
        super(algorithm, decoder);
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public byte[] getDecodePublicKey() {
        return decode(getPublicKey());
    }


    public byte[] getDecodePrivateKey() {
        return decode(getPrivateKey());
    }

    private byte[] decode(String data) {
        return super.decoder.decode(data);
    }
}
