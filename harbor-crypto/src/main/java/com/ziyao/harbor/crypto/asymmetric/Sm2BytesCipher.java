package com.ziyao.harbor.crypto.asymmetric;

import com.ziyao.harbor.crypto.Algorithm;
import com.ziyao.harbor.crypto.BytesCipher;
import lombok.Getter;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
@Getter
public class Sm2BytesCipher implements BytesCipher {

    private final String algorithm = Algorithm.SM2;

    private final SM2 sm2;

    public Sm2BytesCipher(byte[] privateKey, byte[] publicKey) {
        this.sm2 = new SM2(privateKey, publicKey);
    }

    public Sm2BytesCipher(SM2 sm2) {
        this.sm2 = sm2;
    }

    @Override
    public byte[] decrypt(byte[] encrypt) {
        return sm2.decrypt(encrypt);
    }

    @Override
    public byte[] encrypt(byte[] input) {
        return sm2.encrypt(input);
    }
}
