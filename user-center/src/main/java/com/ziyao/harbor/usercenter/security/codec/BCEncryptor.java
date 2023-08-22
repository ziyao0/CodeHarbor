package com.ziyao.harbor.usercenter.security.codec;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public class BCEncryptor implements Encryptor {

    @Override
    public String encode(CharSequence plaintext) {
        return BCrypt.hashpw(plaintext.toString(), BCrypt.gensalt());
    }

    @Override
    public boolean matches(CharSequence plaintext, String ciphertext) {
        return BCrypt.checkpw(plaintext.toString(), ciphertext);
    }

    public static void main(String[] args) {
        System.out.println(new BCEncryptor().encode("admin"));
    }
}
