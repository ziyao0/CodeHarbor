package com.ziyao.harbor.usercenter.authentication.codec;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Randoms;
import com.ziyao.harbor.core.utils.RegexPool;
import com.ziyao.harbor.crypto.digest.BCrypt;
import com.ziyao.harbor.crypto.digest.BCryptVersion;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public final class BCryptPasswordEncryptor implements PasswordEncryptor {

    /**
     * bcrypt 正则表达式
     */
    private final Pattern BCRYPT_PATTERN = RegexPool.BCRYPT_PATTERN.forName();
    private final int strength;
    private final BCryptVersion version;
    private final SecureRandom random;

    public BCryptPasswordEncryptor() {
        this.version = BCryptVersion.$2A;
        this.strength = 8;
        this.random = Randoms.getSecureRandom();
    }

    @Override
    public String encrypt(CharSequence plaintext) {
        Assert.notNull(plaintext, "密码不能为空！");
        String salt = getSalt();
        return BCrypt.hashpw(plaintext.toString(), salt);
    }

    @Override
    public boolean matches(CharSequence plaintext, String ciphertext) {
        Assert.notNull(plaintext, "明文不能为空！");
        Assert.isTrue(plaintext.isEmpty(), "明文不能为空！");
        Assert.notNull(ciphertext, "密文不能为空！");
        Assert.isTrue(!this.BCRYPT_PATTERN.matcher(ciphertext).matches(), "Encoded password does not look like BCrypt");
        return BCrypt.checkpw(plaintext.toString(), ciphertext);
    }

    private String getSalt() {
        if (this.random != null) {
            return BCrypt.gensalt(this.version.getVersion(), this.strength, this.random);
        }
        return BCrypt.gensalt(this.version.getVersion(), this.strength);
    }

    public static void main(String[] args) {
        BCryptPasswordEncryptor BCryptPasswordAuthenticator = new BCryptPasswordEncryptor();
        System.out.println(BCryptPasswordAuthenticator.encrypt("1qaz@WSX"));

        String pd = "$2a$10$VlNsaSfV6KTFadxJ3BkSo.wPvkdblhAWdX9ymJhJ6D2k7PLVbcEnC";
        System.out.println(BCryptPasswordAuthenticator.BCRYPT_PATTERN.matcher(pd).matches());
        System.out.println(BCryptPasswordAuthenticator.matches("admin", pd));

        String admin = com.ziyao.harbor.crypto.digest.BCrypt.hashpw("admin", com.ziyao.harbor.crypto.digest.BCrypt.gensalt());
        System.out.println(admin);
        System.out.println(BCryptPasswordAuthenticator.BCRYPT_PATTERN.matcher(admin).matches());

        System.out.println(BCryptPasswordAuthenticator.matches("admin", admin));

        System.out.println(com.ziyao.harbor.crypto.digest.BCrypt.checkpw("admin", pd));
    }
}
