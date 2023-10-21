package com.ziyao.harbor.crypto.symmetric;

import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.Algorithm;
import com.ziyao.harbor.crypto.Padding;
import com.ziyao.harbor.crypto.TextCipher;
import lombok.Getter;


import java.io.Serializable;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Getter
public class Sm4TextCipher implements TextCipher, Serializable {

    
    private static final long serialVersionUID = 6505931393405080942L;

    private final String algorithm = Algorithm.SM4;


    private final SM4 sm4;

    public Sm4TextCipher(Mode mode, Padding padding, byte[] key, byte[] iv) {
        this.sm4 = new SM4(mode, padding, key, iv);
    }

    public Sm4TextCipher(Mode mode, Padding padding, String key, String iv) {
        this.sm4 = new SM4(mode, padding, key, iv);
    }

    @Override
    public String decrypt(String ciphertext) {
        byte[] decrypt = sm4.decrypt(ciphertext);
        return Strings.toString(decrypt);
    }

    @Override
    public String encrypt(String plaintext) {
        byte[] encrypt = sm4.encrypt(plaintext);
        return HexUtils.encodeHexStr(encrypt);
    }
}
