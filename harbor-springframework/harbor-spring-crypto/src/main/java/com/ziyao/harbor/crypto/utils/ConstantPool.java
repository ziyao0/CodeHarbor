package com.ziyao.harbor.crypto.utils;

/**
 * @author ziyao zhang
 * @since 2023/10/25
 */
public interface ConstantPool {

    String properties_prefix = "properties_prefix_";
    String default_prefix = "com.ziyao";
    String cipher_prefix = "com.ziyao.crypto.cipher";

    /**
     * Name of the decrypted property source.
     */
    public static final String CIPHER_PROPERTY_SOURCE_NAME = "harbor_cipher";

    /**
     * Name of the decrypted bootstrap property source.
     */
    public static final String CIPHER_BOOTSTRAP_PROPERTY_SOURCE_NAME = "harbor_cipher_bootstrap";
}
