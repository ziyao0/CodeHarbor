package com.ziyao.harbor.crypto.symmetric;

import lombok.Getter;

import javax.crypto.Cipher;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
@Getter
public enum CipherMode {
    /**
     * 加密模式
     */
    encrypt(Cipher.ENCRYPT_MODE),
    /**
     * 解密模式
     */
    decrypt(Cipher.DECRYPT_MODE),
    /**
     * 包装模式
     */
    wrap(Cipher.WRAP_MODE),
    /**
     * 拆包模式
     */
    unwrap(Cipher.UNWRAP_MODE);


    /**
     * 构造
     *
     * @param value 见{@link Cipher}
     */
    CipherMode(int value) {
        this.value = value;
    }

    /**
     * -- GETTER --
     * 获取枚举值对应的int表示
     *
     * @return 枚举值对应的int表示
     */
    private final int value;

}
