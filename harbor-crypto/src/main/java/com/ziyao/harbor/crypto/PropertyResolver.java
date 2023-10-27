package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.utils.Strings;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author ziyao
 * @since 2023/4/23
 */

public record PropertyResolver(TextCipherProvider textCipherProvider) {

    /**
     * 解析配置属性值并对配置属性值进行解析，
     *
     * @param key   key
     * @param value 属性值
     * @return {@link Property}
     */
    public Property getProperty(String key, String value) {
        Property property = new Property(key, value);

        if (Strings.hasText(value)) {
            if (isMatchPrefix(value)) {
                // 满足前缀匹配算法
                TextCipher textCipher = matchingTextCipher(textCipherProvider().textCiphers(), value);
                property.setValue(value.substring(getPrefix(textCipher.getAlgorithm()).length()));
                property.setAlgorithm(textCipher.getAlgorithm());
                property.setTextCipher(textCipher);
            }
        }
        return property;
    }


    private static boolean isMatchPrefix(String value) {
        return Stream.of(Algorithm.SM2, Algorithm.SM3, Algorithm.SM4)
                .anyMatch(algorithm ->
                        Strings.startsWithIgnoreCase(value, getPrefix(algorithm)));
    }


    private static TextCipher matchingTextCipher(List<TextCipher> ciphers, String value) {
        for (TextCipher textCipher : ciphers) {
            if (isMatchCipher(textCipher, value)) {
                return textCipher;
            }
        }
        throw new IllegalStateException("未获取到对应的解密器！value=" + value);
    }

    private static boolean isMatchCipher(TextCipher textCipher, String value) {
        String prefix = getPrefix(textCipher.getAlgorithm());
        return Strings.startsWithIgnoreCase(value, prefix);
    }

    private static String getPrefix(String algorithm) {
        return "{" + algorithm + "}";
    }

    @Override
    public TextCipherProvider textCipherProvider() {
        return textCipherProvider;
    }
}
