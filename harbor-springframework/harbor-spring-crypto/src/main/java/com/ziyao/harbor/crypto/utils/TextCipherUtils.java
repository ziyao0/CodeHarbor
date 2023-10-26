package com.ziyao.harbor.crypto.utils;

import com.ziyao.harbor.crypto.TextCipher;
import com.ziyao.harbor.crypto.core.CodebookProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author ziyao
 * @since 2023/4/23
 */

public abstract class TextCipherUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextCipherUtils.class);

    /**
     * 从配置文件中解析对应的文本解码器
     *
     * @param properties 配置秘钥信息
     * @return 返回文本解码器
     */
    public static List<TextCipher> loadCipher(CodebookProperties properties) {
        List<TextCipher> textCiphers = new java.util.ArrayList<>();
        //解析配置文件
        for (CodebookProperties.Type type : properties.getTypes()) {
            switch (type) {
                case sm2 ->
                        textCiphers.add(SmUtils.createSm2TextCipher(properties.getSm2().getPrivateKey(), properties.getSm2().getPublicKey()));
                case sm4 -> {
                    switch (properties.getSm4().getMode()) {
                        case CBC ->
                                textCiphers.add(SmUtils.createSm4CBCTextCipherWithZeroPaddingAndHexCodec(properties.getSm4().getKey(), properties.getSm4().getIv()));
                        case ECB ->
                                textCiphers.add(SmUtils.createSm4ECBTextCipherWithZeroPaddingAndHexCodec(properties.getSm4().getKey(), properties.getSm4().getIv()));
                        default -> LOGGER.error("未知的配置类型");
                    }
                }
                default -> LOGGER.error("未知的配置类型");
            }
        }
        return textCiphers;
    }
}
