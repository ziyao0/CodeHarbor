package com.ziyao.harbor.usercenter.service;

import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedRequest;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface AuthenticatedService {
    /**
     * 用户登陆功能
     *
     * @param authenticatedRequest 认证请求参数
     * @return 返回认证后信息
     */
    String login(AuthenticatedRequest authenticatedRequest);
}
