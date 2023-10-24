package com.ziyao.harbor.crypto;

import com.ziyao.harbor.core.utils.Strings;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ziyao zhang
 * @since 2023/10/23
 */
@Getter
@Setter
public class Property {

    private String key;

    private String value;

    private String algorithm;

    private TextCipher textCipher;

    public Property() {
    }

    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public boolean isEncryption() {
        return !Strings.isEmpty(textCipher);
    }
}
