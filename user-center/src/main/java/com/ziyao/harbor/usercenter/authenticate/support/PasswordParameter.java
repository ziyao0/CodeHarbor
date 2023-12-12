package com.ziyao.harbor.usercenter.authenticate.support;

/**
 * 密码参数
 *
 * @param plaintext  明文
 * @param ciphertext 密文
 * @author ziyao zhang
 * @since 2023/12/12
 */
public record PasswordParameter(CharSequence plaintext, String ciphertext) {

    public static PasswordParameter of(CharSequence plaintext, String ciphertext) {
        return new PasswordParameter(plaintext, ciphertext);
    }
}
