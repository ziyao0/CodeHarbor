package com.ziyao.harbor.web.context;

import com.ziyao.harbor.web.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
public interface ContextInfo {

    /**
     * 获取 {@link HttpServletRequest}.
     *
     * @return HttpServletRequest
     */
    HttpServletRequest request();

    /**
     * 获取 {@link HttpServletResponse}.
     *
     * @return HttpServletResponse
     */
    HttpServletResponse response();

    /**
     * 判断请求是否认证, 未认证的请求用户信息将为<code>null</code>.
     *
     * @return <code>false</code> 请求未认证
     */
    boolean isAuthentication();

    /**
     * 断言当前请求是否认证，未认证会抛出异常{@link com.ziyao.harbor.web.exception.UnauthorizedException}.
     */
    void assertAuthentication();

    UserDetails getUser();
}
