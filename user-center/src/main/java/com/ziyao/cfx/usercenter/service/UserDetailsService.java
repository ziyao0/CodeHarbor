package com.ziyao.cfx.usercenter.service;

import com.ziyao.cfx.usercenter.security.api.UserDetails;

/**
 * @author zhangziyao
 * @date 2023/4/24
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
