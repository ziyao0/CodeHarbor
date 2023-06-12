package com.ziyao.cfx.usercenter.security.core;

/**
 * <p>
 * 多因子身份认证
 * 简介：多因子身份认证（Multi-factor Authentication Service。）的目的是建立一个多层次的防御体系，
 * 通过结合两种或三种认证因子（基于记忆的/基于持有物的/基于生物特征的认证因子）验证访问者的身份，
 * 使系统或资源更加安全。攻击者即使破解单一因子（如短信等），应用的安全依然可以得到保障。
 * </p>
 *
 * @author zhangziyao
 * @date 2023/4/24
 */
public interface MultiFactorAuthProvider extends AuthenticationProvider {
}
