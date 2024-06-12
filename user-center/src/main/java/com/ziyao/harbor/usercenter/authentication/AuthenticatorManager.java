package com.ziyao.harbor.usercenter.authentication;

import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.provider.OAuth2Authenticator;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;

import java.util.List;
import java.util.Map;

/**
 * 身份认证管理器
 *
 * @author ziyao
 * @since 2023/4/23
 */
@FunctionalInterface
public interface AuthenticatorManager {
    /**
     * 认证处理 映射
     *
     * @param request {@link AuthenticationRequest}认证核心参数
     * @return 返回认证结果
     */
    Authentication authenticate(AuthenticationRequest request);

    /**
     * 初始化BeanName
     *
     * @param authenticators 认证bean
     * @return name to beanName
     */
    default Map<AuthorizationGrantType, OAuth2Authenticator> initBeanMapping(List<? extends OAuth2Authenticator> authenticators) {
        return null;
    }
}
