package com.ziyao.harbor.usercenter.authenticate.support;

import lombok.Getter;

/**
 * 密码参数
 *
 * @author ziyao zhang
 * @since 2023/12/12
 */
@Getter
public class PasswordParameter {

    private final CharSequence plaintext;
    private final String ciphertext;

    public PasswordParameter(CharSequence plaintext, String ciphertext) {
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
    }


    public static PasswordParameter of(CharSequence plaintext, String ciphertext) {
        return new PasswordParameter(plaintext, ciphertext);
    }
}
