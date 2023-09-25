package com.ziyao.harbor.usercenter.security.codec;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public class PasswordAuthenticator implements Encryptor {

    @Override
    public String encrypt(CharSequence plaintext) {
        return BCrypt.hashpw(plaintext.toString(), BCrypt.gensalt());
    }

    @Override
    public boolean matches(CharSequence plaintext, String ciphertext) {
        return BCrypt.checkpw(plaintext.toString(), ciphertext);
    }

    public static void main(String[] args) {
        PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
        System.out.println(passwordAuthenticator.encrypt("admin"));

        String pd = "$2a$10$VlNsaSfV6KTFadxJ3BkSo.wPvkdblhAWdX9ymJhJ6D2k7PLVbcEnC";

        System.out.println(passwordAuthenticator.matches("admin", pd));
    }
}
