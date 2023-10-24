package com.ziyao.harbor.crypto.encrypt;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.CipherProperties;
import com.ziyao.harbor.crypto.TextCipher;
import com.ziyao.harbor.crypto.utils.SmUtils;
import lombok.Getter;

import java.util.List;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Getter
public class TextCipherFactory {


    private final List<TextCipher> textCiphers;

    public TextCipherFactory(List<TextCipher> textCiphers) {
        this.textCiphers = textCiphers;
    }

    public TextCipherFactory(CipherProperties properties) {
        this.textCiphers = readCipher(properties);
    }

    public TextCipher loadCipher(String algorithm) {
        if (Strings.hasText(algorithm)) {
            for (TextCipher textCipher : getTextCiphers()) {
                if (Strings.equalsIgnoreCase(algorithm, textCipher.getAlgorithm())) {
                    return textCipher;
                }
            }
        }
        return null;
    }

    private List<TextCipher> readCipher(CipherProperties properties) {
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
