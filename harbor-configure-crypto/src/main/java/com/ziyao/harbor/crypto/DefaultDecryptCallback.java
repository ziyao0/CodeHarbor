package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.Ordered;
import com.ziyao.harbor.crypto.utils.SmUtils;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class DefaultDecryptCallback implements DecryptCallback {
    private static final int order = Ordered.LOWEST_PRECEDENCE;

    private final TextCipher textCipher;

    public DefaultDecryptCallback() {
        this.textCipher = createTextCipher();
    }

    private TextCipher createTextCipher() {
        String key = System.getProperty(ConstantPool.KEY);
        String iv = System.getProperty(ConstantPool.IV);
        return SmUtils.createSm4CBCTextCipherWithZeroPaddingAndHexCodec(key, iv);
    }

    @Override
    public String decrypt(String encrypt) throws Exception {
        return textCipher.decrypt(encrypt);
    }

    @Override
    public int getOrder() {
        return order;
    }
}
