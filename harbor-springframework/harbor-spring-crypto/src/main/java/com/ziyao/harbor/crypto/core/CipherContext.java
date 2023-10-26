package com.ziyao.harbor.crypto.core;

/**
 * @author ziyao zhang
 * @since 2023/10/24
 */
public interface CipherContext {

    CodebookProperties getProperties();

    PropertyResolver getPropertyResolver();

    TextCipherProvider getTextCipherProvider();

}
