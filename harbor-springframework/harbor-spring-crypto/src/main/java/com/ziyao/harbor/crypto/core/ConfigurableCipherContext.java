package com.ziyao.harbor.crypto.core;

import lombok.Getter;

/**
 * @author ziyao zhang
 * @since 2023/10/25
 */
@Getter
public class ConfigurableCipherContext implements CipherContext {

    private CodebookProperties properties;

    private PropertyResolver propertyResolver;

    private TextCipherProvider textCipherProvider;

    public void setProperties(CodebookProperties properties) {
        this.properties = properties;
    }

    public void setPropertyResolver(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
    }

    public void setTextCipherProvider(TextCipherProvider textCipherProvider) {
        this.textCipherProvider = textCipherProvider;
    }
}
