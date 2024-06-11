package com.ziyao.harbor.usercenter.authentication.codec;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public interface PasswordEncryptor {

    /**
     * <p>
     * 对初始密码进行加密 该方法是对参数进行加密
     * </p>
     *
     * @param plaintext 原始明文
     * @return 加密后的密码
     */
    String encrypt(CharSequence plaintext);

    /**
     * <p>
     * 验证从存储中获取的编码密码是否与提交的原始密码匹配后也被编码。 如果密码匹配，则返回 true，否则返回 false。存储的密码本身永远不会被解码。
     * </p>
     *
     * @param plaintext  要编码和匹配的原始密码
     * @param ciphertext 数据库编码后的密码
     * @return 如果原始密码在编码后与存储中的编码密码匹配，则为 true
     */
    boolean matches(CharSequence plaintext, String ciphertext);

}
