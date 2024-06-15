package com.ziyao.harbor.usercenter.authentication;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.usercenter.authentication.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.provider.OAuth2Authenticator;
import com.ziyao.harbor.usercenter.support.Logs;
import org.slf4j.Logger;

import java.util.List;

/**
 * <p>
 * 认证提供管理器，通过实现{@link AuthenticationManager} 认证管理器去处理认证核心逻辑
 * 认证实现是通过委托的设计模式去设计实现的，认证方案统一由{@link OAuth2Authenticator}
 * 来提供.
 * 基于安全考虑，设计之初采用了双重认证（多因子认证）方案，即再{@link OAuth2Authenticator#authenticate(Authentication)}
 * 认证通过的集成上提供了多因子认证方案，即通过{@link AuthenticationManager} 来提供多因子
 * 认证方案,执行{@linkplain AuthenticationManager#authenticate(Authentication)}该认证的前提是，首次认证的结果
 * {@link AuthenticatedUser#isAuthenticated()}必须为true，false或者null则代表在第一重验证的时候没有通过.
 * </p>
 *
 * @author ziyao zhang
 * @time 2024/6/15
 */
public class PrimaryAuthenticationManager implements AuthenticationManager {

    private static final Logger log = Logs.getLogger(PrimaryAuthenticationManager.class);

    private final List<OAuth2Authenticator> authenticators;

    public PrimaryAuthenticationManager(List<OAuth2Authenticator> authenticators) {
        Assert.notNull(authenticators, "身份验证器不能为空");
        this.authenticators = authenticators;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        for (OAuth2Authenticator authenticator : this.authenticators) {
            if (authenticator.supports(authentication.getClass())) {
                return authenticator.authenticate(authentication);
            }
        }
        log.error("没有支持的身份验证器：{}", authentication.getClass());
        return null;
    }
}
