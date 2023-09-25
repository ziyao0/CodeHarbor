package com.ziyao.harbor.usercenter.security;

import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/9/25
 */
public interface Authenticator {
    /**
     * 身份验证器是否需要显式登录。
     * 如果为 false，则将用AuthenticatedUser.ANONYMOUS_USER实例化用户。
     */
    boolean requireAuthentication();

    /**
     * 应使用户无法访问且只能在内部访问的资源集。
     *
     * @return 键空间，用户无法修改的列族;其他资源。
     */
    Set<? extends IResource> protectedResources();

    public boolean isComplete();

//    public AuthenticatedUser getAuthenticatedUser() throws AuthenticationException;
}
