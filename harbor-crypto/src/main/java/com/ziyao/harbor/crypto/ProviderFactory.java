package com.ziyao.harbor.crypto;

import com.ziyao.harbor.crypto.utils.Secures;

import java.security.Provider;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public class ProviderFactory {

    /**
     * 创建Bouncy Castle 提供者<br>
     * 如果用户未引入bouncycastle库，则此方法抛出{@link NoClassDefFoundError} 异常
     *
     * @return {@link Provider}
     */
    public static Provider createBouncyCastleProvider() {
        final org.bouncycastle.jce.provider.BouncyCastleProvider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
        // issue#2631@Github
        Secures.addProvider(provider);
        return provider;
    }
}
