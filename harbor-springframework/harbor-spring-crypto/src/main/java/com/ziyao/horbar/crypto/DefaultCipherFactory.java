package com.ziyao.horbar.crypto;

import com.ziyao.harbor.crypto.TextCipher;
import com.ziyao.harbor.crypto.utils.SmUtils;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public class DefaultCipherFactory implements CipherFactory {


    @Override
    public List<TextCipher> create(Object properties) {
        List<TextCipher> textCiphers = new java.util.ArrayList<>();
        //解析配置文件
        CipherProperties secret = parseProperties(properties);
        for (CipherProperties.Type type : secret.getTypes()) {
            switch (type) {
                case sm2 -> {
                    textCiphers.add(SmUtils.createSm2TextCipher(secret.getSm2().getPriKey(), secret.getSm2().getPubKey()));
                }
                case sm4 -> {
                    switch (secret.getSm4().getMode()) {
                        case CBC -> {
                            textCiphers.add(SmUtils.createSm4CBCTextCipherWithZeroPaddingAndHexCodec(secret.getSm4().getKey(), secret.getSm4().getIv()));
                        }
                        case ECB -> {
                            textCiphers.add(SmUtils.createSm4ECBTextCipherWithZeroPaddingAndHexCodec(secret.getSm4().getKey(), secret.getSm4().getIv()));
                        }
                        default -> {
                            throw new IllegalArgumentException("未知的配置类型");
                        }
                    }
                }
                default -> throw new IllegalArgumentException("未知的配置类型");
            }
        }
        return textCiphers;
    }

    private CipherProperties parseProperties(Object properties) {

        return new CipherProperties();
    }


}
