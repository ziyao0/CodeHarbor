package com.cfx.usercenter.security.support;

import com.cfx.usercenter.security.api.Authentication;
import org.springframework.util.ObjectUtils;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public abstract class SecurityUtils {

    public static final String REFRESH_TOKEN = "/user-center-service/center/auth/token/refresh";
    public static final String OAUTH2_SECURITY = "cfx:oauth2:security";
    public static final String TEMP_SECURITY_SECRET = "cfx:temp:secret";
    public static final String TOKEN_GROUP = "token-group";

    private SecurityUtils() {
    }

    /**
     * 判断当前认证对象是否是一个成功的认证对象
     *
     * @return 返回<code>true</code>为认证成功、返回继续下一次认证
     * @see com.cfx.usercenter.security.api.Authentication#isAuthenticated()
     */
    public static boolean authenticated(Authentication authentication) {
        return !ObjectUtils.isEmpty(authentication) && authentication.isAuthenticated();
    }

//    /**
//     * 判断当前认证对象是否是一个成功的授权对象
//     *
//     * @return 返回<code>true</code>为授权检查成功、返回继续下一次授权检查
//     * @see Authorization#isAuthorized()
//     */
//    public static Predicate<Authorization> authorized() {
//        return authorization -> !ObjectUtils.isEmpty(authorization) && authorization.isAuthorized();
//    }
//
//    /**
//     * 判断本次请求的授权对象是否为安全对象
//     * <p>
//     * 例如是否为白名单、或者是否需要校验ticket等等
//     *
//     * @return <code>true</code> 代表本次请求是安全的，不需要再进行权限校验，反之则需要进行校验
//     */
//    public static Predicate<Authorization> isSecurity() {
//        return authorization -> !ObjectUtils.isEmpty(authorization) && authorization.isSecurity();
//    }
}

