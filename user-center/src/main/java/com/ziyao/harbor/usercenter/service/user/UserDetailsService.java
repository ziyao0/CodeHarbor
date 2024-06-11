package com.ziyao.harbor.usercenter.service.user;

import com.ziyao.harbor.core.lang.Nullable;
import com.ziyao.harbor.usercenter.authentication.core.UserDetails;

/**
 * @author ziyao
 * @since 2024/06/11 15:51:59
 */
public interface UserDetailsService {

    /**
     * 通过用户名获取用户信息
     *
     * @param username 用户登陆名
     * @return 返回用户信息
     */
    @Nullable
    UserDetails loadUserByUsername(String username);

}
