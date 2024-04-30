package com.ziyao.harbor.crypto.druid;

import com.ziyao.harbor.crypto.TextCipher;

import javax.security.auth.callback.NameCallback;
import java.io.Serial;

/**
 * @author ziyao zhang
 * @since 2023/10/18
 */
public class NameDecryptCallback extends NameCallback {
    @Serial
    private static final long serialVersionUID = 6060803312395586003L;

    private final TextCipher textCipher;


    public NameDecryptCallback(String prompt, TextCipher textCipher) {
        super(prompt);
        this.textCipher = textCipher;
    }

    @Override
    public String getName() {
        return textCipher.decrypt(super.getName());
    }
}
