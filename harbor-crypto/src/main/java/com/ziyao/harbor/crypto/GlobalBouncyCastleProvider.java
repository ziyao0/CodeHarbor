package com.ziyao.harbor.crypto;

import lombok.Setter;

import java.security.Provider;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public enum GlobalBouncyCastleProvider {
    /**
     * 单例
     */
    INSTANCE;

    private Provider provider;
    /**
     * -- SETTER --
     *  设置是否使用Bouncy Castle库<br>
     *  如果设置为false，表示强制关闭Bouncy Castle而使用JDK
     *
     * @code isUseBouncyCastle 是否使用BouncyCastle库
     */
    @Setter
    private static boolean useBouncyCastle = true;

    GlobalBouncyCastleProvider() {
        try {
            this.provider = ProviderFactory.createBouncyCastleProvider();
        } catch (NoClassDefFoundError | NoSuchMethodError e) {
            // ignore
        }
    }

    /**
     * 获取{@link Provider}
     *
     * @return {@link Provider}
     */
    public Provider getProvider() {
        return useBouncyCastle ? this.provider : null;
    }

}
