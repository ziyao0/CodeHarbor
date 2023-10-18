package com.ziyao.harbor.crypto.asymmetric;

import lombok.Getter;

import javax.crypto.Cipher;

/**
 * 秘钥类型
 *
 * @author ziyao zhang
 * @since 2023/10/18
 */
@Getter
public enum KeyType {
    /**
     * 公钥
     */
    PublicKey(Cipher.PUBLIC_KEY),
    /**
     * 私钥
     */
    PrivateKey(Cipher.PRIVATE_KEY),
    /**
     * 密钥
     */
    SecretKey(Cipher.SECRET_KEY);


    /**
     * 构造
     *
     * @param value 见{@link Cipher}
     */
    KeyType(int value) {
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
