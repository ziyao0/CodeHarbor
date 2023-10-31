package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.Ordered;
import com.ziyao.harbor.crypto.utils.SmUtils;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class DefaultEncryptCallback implements EncryptCallback {

    private static final int order = Ordered.LOWEST_PRECEDENCE;

    private final TextCipher textCipher;

    public DefaultEncryptCallback() {
        this.textCipher = createTextCipher();
    }

    private TextCipher createTextCipher() {
        String key = System.getProperty(ConstantPool.key);
        String iv = System.getProperty(ConstantPool.iv);
        return SmUtils.createSm4CBCTextCipherWithZeroPaddingAndHexCodec(key, iv);
    }

    @Override
    public String encrypt(String text) throws Exception {
        return PropertyResolver.getPrefix(textCipher.getAlgorithm()) + textCipher.encrypt(text);
    }

    @Override
    public int getOrder() {
        return order;
    }
}
