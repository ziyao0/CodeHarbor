package com.cfx.usercenter.security.codec;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Eason
 * @since 2023/5/8
 */
public class DefaultPdEncryptor implements Encryptor {

    @Override
    public String encode(CharSequence plaintext) {
        return BCrypt.hashpw(plaintext.toString(), BCrypt.gensalt());
    }

    @Override
    public boolean matches(CharSequence plaintext, String ciphertext) {
        return BCrypt.checkpw(plaintext.toString(), ciphertext);
    }
}
