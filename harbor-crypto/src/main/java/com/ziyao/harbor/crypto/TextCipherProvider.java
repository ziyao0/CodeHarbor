package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.utils.Strings;
import lombok.Getter;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/10/25
 */
@Getter
public class TextCipherProvider {

    private final List<TextCipher> textCiphers;

    public TextCipherProvider(List<TextCipher> textCiphers) {
        this.textCiphers = textCiphers;
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

}
