package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticationType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * @param authenticatedRequest {@link AuthenticatedRequest}认证核心参数
     * @return 返回认证结果
     */
    AuthenticatedUser authenticate(AuthenticatedRequest authenticatedRequest);

    /**
     * 初始化BeanName
     *
     * @param authenticators 认证bean
     * @return name to beanName
     */
    default Map<AuthenticationType, Authenticator> initBeanMapping(List<? extends Authenticator> authenticators) {
        return authenticators.stream().collect(
                Collectors.toMap(Authenticator::getAuthenticationType, Function.identity())
        );
    }
}
