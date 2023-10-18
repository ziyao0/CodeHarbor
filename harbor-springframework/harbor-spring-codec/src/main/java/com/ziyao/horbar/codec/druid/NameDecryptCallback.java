package com.ziyao.horbar.codec.druid;

import javax.security.auth.callback.NameCallback;
import java.io.Serial;

/**
 * @author ziyao zhang
 * @since 2023/10/18
 */
public class NameDecryptCallback extends NameCallback {
    @Serial
    private static final long serialVersionUID = 6060803312395586003L;

    public NameDecryptCallback(String prompt) {
        super(prompt);
    }

    @Override
    public String getName() {
        return super.getName();
    }
}