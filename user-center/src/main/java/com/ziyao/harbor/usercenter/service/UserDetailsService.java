package com.ziyao.harbor.usercenter.service;

import com.ziyao.harbor.usercenter.authentication.core.UserDetails;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public interface UserDetailsService {

    /**
     * 获取用户信息详情
     *
     * @param accessKey 登录名
     * @return 返回用户详情
     */
    UserDetails loadUserDetails(String accessKey);
}
