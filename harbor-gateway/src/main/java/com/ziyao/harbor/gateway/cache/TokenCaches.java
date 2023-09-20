package com.ziyao.harbor.gateway.cache;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public abstract class TokenCaches {
    private static final Set<String> token_cache = new ConcurrentSkipListSet<>();


    public static void offline(String token) {

    }

    public static void online(String token) {

    }


    private TokenCaches() {
    }
}
