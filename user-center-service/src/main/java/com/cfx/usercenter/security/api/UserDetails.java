package com.cfx.usercenter.security.api;

import java.io.Serializable;

/**
 * @author zhangziyao
 * @date 2023/4/24
 */
public interface UserDetails extends Serializable {

    /**
     * 用于校验的用户名
     *
     * @return username
     */
    String getAccessKey();

    /**
     * 用于校验的密码
     *
     * @return pd
     */
    String getSecretKey();

    /**
     * 表示账号是否过期，过期的账号不能登录
     *
     * @return <code>true</code> 没有过期, <code>false</code> 已过期
     */
    boolean isAccountNonExpired();

    /**
     * 标识账号是否被锁定，被锁定的账号无法进行认证
     *
     * @return <code>true</code> not locked, <code>false</code> locked
     */
    boolean isAccountNonLocked();

    /**
     * 用户密码是否过期，过期密码无法校验
     *
     * @return <code>true</code> 用户凭证有效（没有过期）, <code>false</code> 过期
     */
    boolean isCredentialsNonExpired();

    /**
     * 提示账号是启用还是禁用，已禁用的账号是无法登录的
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    boolean isEnabled();

}
