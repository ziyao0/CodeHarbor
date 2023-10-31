package com.ziyao.harbor.crypto.digest;

import lombok.Getter;

/**
 * Stores the default bcrypt version for use in configuration.
 *
 * @author ziyao
 * @since 2023/4/23
 */
@Getter
public enum BCryptVersion {

    $2A("$2a"),

    $2Y("$2y"),

    $2B("$2b");

    private final String version;

    BCryptVersion(String version) {
        this.version = version;
    }
}
