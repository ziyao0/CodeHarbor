package com.cfx.usercenter.security.codec;

/**
 * @author zhangziyao
 * @date 2023/4/24
 */
public interface Encryptor {

    /**
     * <p>
     * 对初始密码进行加密 该方法是对参数进行加密
     * </p>
     *
     * @param pubKey 在sm3中该参数为登录名
     * @param raw    原始明文
     * @return 加密后的密码
     */
    String encode(CharSequence pubKey, CharSequence raw);


    /**
     * <p>
     * 验证从存储中获取的编码密码是否与提交的原始密码匹配后也被编码。 如果密码匹配，则返回 true，否则返回 false。存储的密码本身永远不会被解码。
     * </p>
     *
     * @param raw       要编码和匹配的原始密码
     * @param encodedPd 数据库编码后的密码
     * @return 如果原始密码在编码后与存储中的编码密码匹配，则为 true
     */
    boolean matches(CharSequence raw, String encodedPd);

}
