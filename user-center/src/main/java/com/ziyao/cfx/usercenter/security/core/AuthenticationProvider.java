package com.ziyao.cfx.usercenter.security.core;

import com.ziyao.cfx.usercenter.comm.exception.AuthenticationException;
import com.ziyao.cfx.usercenter.security.api.Authentication;
import com.ziyao.cfx.usercenter.security.api.ProviderName;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public interface AuthenticationProvider extends Ordered, BeanNameAware {

    /**
     * 认证处理
     *
     * @param authentication {@link Authentication}认证核心参数
     * @return 返回认证结果
     * @throws AuthenticationException 认证异常
     */
    Authentication authenticate(Authentication authentication);

    /**
     * 验证是否支持认证协议
     *
     * @param authenticationClass class
     * @return 返回 {@link Boolean#TRUE} 不支持
     */
    default boolean supports(Class<?> authenticationClass) {
        return Authentication.class.isAssignableFrom(authenticationClass);
    }

    /**
     * Provider Name
     *
     * @return {@link ProviderName}
     */
    ProviderName getProviderName();

    /**
     * 获取beanName
     *
     * @return 返回bean名称
     */
    String getBeanName();

    /**
     * 回调设置BeanName
     *
     * @param beanName the name of the bean in the factory.
     *                 Note that this name is the actual bean name used in the factory, which may
     *                 differ from the originally specified name: in particular for inner bean
     *                 names, the actual bean name might have been made unique through appending
     *                 "#..." suffixes. Use the {@link org.springframework.beans.factory.BeanFactoryUtils#originalBeanName(String)}
     *                 method to extract the original bean name (without suffix), if desired.
     */
    @Override
    void setBeanName(@Nullable String beanName);
}
