package com.ziyao.harbor.oauth2.provider.authentication;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public interface AuthenticationDetailsSource<C, T> {
    // ~ Methods
    // ========================================================================================================

    /**
     * Called by a class when it wishes a new authentication details instance to be
     * created.
     *
     * @param context the request object, which may be used by the authentication details
     *                object
     * @return a fully-configured authentication details instance
     */
    T buildDetails(C context);
}