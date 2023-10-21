package com.ziyao.harbor.crypto.asymmetric;

import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.Algorithm;
import com.ziyao.harbor.crypto.TextCipher;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Getter
public class Sm2TextCipher implements TextCipher, Serializable {

    @Serial
    private static final long serialVersionUID = -4304089756836295726L;

    private final String algorithm = Algorithm.SM2;

    private final SM2 sm2;

    public Sm2TextCipher(byte[] privateKey, byte[] publicKey) {
        this.sm2 = new SM2(privateKey, publicKey);
    }

    public Sm2TextCipher(String privateKey, String publicKey) {
        this.sm2 = new SM2(privateKey, publicKey);
    }

    @Override
    public String decrypt(String ciphertext) {
        byte[] bytes = HexUtils.decodeHex(ciphertext);
        byte[] decrypt = sm2.decrypt(bytes);
        return Strings.toString(decrypt);
    }

    @Override
    public String encrypt(String plaintext) {
        byte[] decrypt = sm2.encrypt(Strings.toBytes(plaintext));
        return HexUtils.encodeHexStr(decrypt);
    }
}
