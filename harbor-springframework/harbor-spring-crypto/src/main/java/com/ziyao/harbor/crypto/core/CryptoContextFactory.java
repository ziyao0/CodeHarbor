package com.ziyao.harbor.crypto.core;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public interface CryptoContextFactory {

    CipherContext createContext(ConfigurableApplicationContext applicationContext);

    CipherContext createContext(ConfigurableEnvironment environment);
}
