package com.ziyao.harbor.usercenter.authentication.core;

/**
 * @author ziyao
 * @since 2024/06/11 14:56:37
 */
public interface CredentialsContainer {

    /**
     * 擦除凭证
     * <p>
     * 例如密码等
     */
    void eraseCredentials();
}
