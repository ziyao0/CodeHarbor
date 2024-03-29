package com.ziyao.harbor.usercenter.authenticate.support;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authenticate.core.UserDetails;
import com.ziyao.harbor.usercenter.comm.exception.Errors;
import com.ziyao.harbor.usercenter.comm.exception.UserStatusException;

/**
 * 检查用户状态公共类
 *
 * @author zhangziyao
 * @since 2023/4/24
 */
public abstract class UserStatusValidator implements UserDetailsValidator {

    private static final UserStatusValidator userStatusValidator = new UserStatusValidator() {
        @Override
        public void validate(UserDetails userDetails) {
            super.validate(userDetails);
        }
    };

    public static void validated(UserDetails userDetails) {
        userStatusValidator.validate(userDetails);
    }

    /**
     * 检查用户状态
     *
     * @param userDetails the UserDetails instance whose status should be checked.
     */
    @Override
    public void validate(UserDetails userDetails) {
        // 账号
        if (Strings.isEmpty(userDetails.getAccessKey())) {
            throw new UserStatusException(Errors.ERROR_100005);
        }
        // 密码
        if (Strings.isEmpty(userDetails.getSecretKey())) {
            throw new UserStatusException(Errors.ERROR_100006);
        }

        if (!userDetails.isAccountNonLocked()) {
            throw new UserStatusException(Errors.ERROR_100001);
        }
        if (!userDetails.isEnabled()) {
            throw new UserStatusException(Errors.ERROR_100002);
        }
        if (!userDetails.isAccountNonExpired()) {
            throw new UserStatusException(Errors.ERROR_100003);
        }
        if (!userDetails.isCredentialsNonExpired()) {
            throw new UserStatusException(Errors.ERROR_100004);
        }
    }
}
