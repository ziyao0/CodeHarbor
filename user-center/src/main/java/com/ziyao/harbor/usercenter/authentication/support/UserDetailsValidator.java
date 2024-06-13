package com.ziyao.harbor.usercenter.authentication.support;

import com.ziyao.harbor.usercenter.authentication.core.UserDetails;
import com.ziyao.harbor.usercenter.common.exception.Errors;
import com.ziyao.harbor.usercenter.common.exception.UserStatusException;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public abstract class UserDetailsValidator {

    /**
     * 检查账号信息是否为空
     *
     * @param check 待检测对象
     */
    public static void assertExists(UserDetails check) {
        if (check == null) {
            throw new UserStatusException(Errors.ERROR_100005);
        }
    }

    /**
     * 检查用户
     *
     * @param check the UserDetails instance whose status should be checked.
     * @throws com.ziyao.harbor.web.exception.ServiceException 检查未通过抛出异常
     */
    public static void validated(UserDetails check) {
        if (!check.isAccountNonLocked()) {
            throw new UserStatusException(Errors.ERROR_100001);
        }
        if (!check.isEnabled()) {
            throw new UserStatusException(Errors.ERROR_100002);
        }
        if (!check.isAccountNonExpired()) {
            throw new UserStatusException(Errors.ERROR_100003);
        }
        if (!check.isCredentialsNonExpired()) {
            throw new UserStatusException(Errors.ERROR_100004);
        }
    }

    public UserDetailsValidator() {
    }
}