package com.ziyao.harbor.gateway.support;

/**
 * @author ziyao
 * @since 2024/05/28 10:06:29
 */
public abstract class RedisKeys {

    private static final String DEBOUNCE_PR = "ziyao:gateway:debounce:";

    public static String getDebounceKeyByValue(String value) {
        return DEBOUNCE_PR + value;
    }

    private RedisKeys() {
    }
}
