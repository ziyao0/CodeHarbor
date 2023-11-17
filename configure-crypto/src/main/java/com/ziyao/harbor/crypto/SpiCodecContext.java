package com.ziyao.harbor.crypto;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class SpiCodecContext implements CodecContext {

    private EncryptCallback encryptCallback;

    private DecryptCallback decryptCallback;

    @Override
    public EncryptCallback getEncryptCallback() {
        return encryptCallback;
    }

    @Override
    public DecryptCallback getDecryptCallback() {
        return decryptCallback;
    }

    public void setEncryptCallback(EncryptCallback encryptCallback) {
        this.encryptCallback = encryptCallback;
    }

    public void setDecryptCallback(DecryptCallback decryptCallback) {
        this.decryptCallback = decryptCallback;
    }
}
