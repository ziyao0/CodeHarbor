package com.ziyao.harbor.crypto.core;

import com.ziyao.harbor.crypto.PropertyResolver;
import com.ziyao.harbor.crypto.TextCipherProvider;

/**
 * @author ziyao zhang
 * @since 2023/10/24
 */
public interface CipherContext {

    CodebookProperties getProperties();

    PropertyResolver getPropertyResolver();

    TextCipherProvider getTextCipherProvider();

    /**
     * 解密失败时是否失败
     *
     * @return {@code false} 打印异常日志
     */
    boolean isFailOnError();

}
