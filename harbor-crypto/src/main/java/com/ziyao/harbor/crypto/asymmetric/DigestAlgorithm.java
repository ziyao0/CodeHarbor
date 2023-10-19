package com.ziyao.harbor.crypto.asymmetric;

import lombok.Getter;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
@Getter
public enum DigestAlgorithm {
    MD2("MD2"),
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512");

    /**
     * -- GETTER --
     * 获取算法字符串表示
     */
    private final String value;

    /**
     * 构造
     *
     * @param value 算法字符串表示
     */
    DigestAlgorithm(String value) {
        this.value = value;
    }

}