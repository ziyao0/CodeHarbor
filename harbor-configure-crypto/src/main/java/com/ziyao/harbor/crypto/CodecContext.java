package com.ziyao.harbor.crypto;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface CodecContext {

    EncryptCallback getEncryptCallback();

    DecryptCallback getDecryptCallback();
}
