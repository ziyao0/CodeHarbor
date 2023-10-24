package com.ziyao.harbor.crypto.encrypt;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.Algorithm;
import com.ziyao.harbor.crypto.TextCipher;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
public final class CipherUtils {

    public static boolean isMatchPrefix(String value) {
        return Stream.of(Algorithm.SM2, Algorithm.SM3, Algorithm.SM4)
                .anyMatch(algorithm ->
                        Strings.startsWithIgnoreCase(value, "{" + algorithm + "}"));
    }


    public static TextCipher matchingTextCipher(List<TextCipher> ciphers, String value) {
        for (TextCipher textCipher : ciphers) {
            if (isMatchCipher(textCipher.getAlgorithm(), value)) {
                return textCipher;
            }
        }
        throw new IllegalStateException("未获取到对应的解密器！value=" + value);
    }

    public static boolean isMatchCipher(String algorithm, String value) {
        return Strings.startsWithIgnoreCase(value, "{" + algorithm + "}");
    }
}
