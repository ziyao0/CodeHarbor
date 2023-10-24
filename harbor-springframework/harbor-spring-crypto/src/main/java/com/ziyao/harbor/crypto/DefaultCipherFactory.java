package com.ziyao.harbor.crypto;

import com.ziyao.harbor.crypto.utils.SmUtils;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public class DefaultCipherFactory implements CipherFactory {


    @Override
    public List<TextCipher> create(CipherProperties properties) {
        List<TextCipher> textCiphers = new java.util.ArrayList<>();
        //解析配置文件
        for (CipherProperties.Type type : properties.getTypes()) {
            switch (type) {
                case sm2 ->
                        textCiphers.add(SmUtils.createSm2TextCipher(properties.getSm2().getPriKey(), properties.getSm2().getPubKey()));
                case sm4 -> {
                    switch (properties.getSm4().getMode()) {
                        case CBC ->
                                textCiphers.add(SmUtils.createSm4CBCTextCipherWithZeroPaddingAndHexCodec(properties.getSm4().getKey(), properties.getSm4().getIv()));
                        case ECB ->
                                textCiphers.add(SmUtils.createSm4ECBTextCipherWithZeroPaddingAndHexCodec(properties.getSm4().getKey(), properties.getSm4().getIv()));
                        default -> throw new IllegalArgumentException("未知的配置类型");
                    }
                }
                default -> throw new IllegalArgumentException("未知的配置类型");
            }
        }
        return textCiphers;
    }
}
