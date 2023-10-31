package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.codec.StringCodec;
import com.ziyao.harbor.core.utils.Strings;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public class DefaultTextCipher implements TextCipher {

    private final BytesCipher bytesCipher;

    private final StringCodec stringCodec;

    public DefaultTextCipher(BytesCipher bytesCipher, StringCodec stringCodec) {
        this.bytesCipher = bytesCipher;
        this.stringCodec = stringCodec;
    }

    @Override
    public String getAlgorithm() {
        return bytesCipher.getAlgorithm();
    }

    @Override
    public StringCodec getCodec() {
        return stringCodec;
    }

    @Override
    public String decrypt(String encrypt) {
        byte[] decode = stringCodec.decode(encrypt);
        byte[] decrypt = bytesCipher.decrypt(decode);
        return Strings.toString(decrypt);
    }

    @Override
    public String encrypt(String input) {
        byte[] encrypt = bytesCipher.encrypt(Strings.toBytes(input));
        return stringCodec.encode(encrypt);
    }
}
