package com.ziyao.harbor.crypto.core;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public interface CryptoContextFactory {

    CryptoContext createContext(ConfigurableApplicationContext applicationContext);
}
