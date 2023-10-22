package com.ziyao.harbor.usercenter.security;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.comm.exception.ErrorsIMessage;
import com.ziyao.harbor.usercenter.comm.exception.UserStatusException;
import com.ziyao.harbor.usercenter.security.api.UserDetails;
import com.ziyao.harbor.usercenter.security.core.UserDetailsChecker;

/**
 * 检查用户状态公共类
 *
 * @author zhangziyao
 * @since 2023/4/24
 */
public class UserStatusChecker implements UserDetailsChecker {


    /**
     * 检查用户状态
     *
     * @param userDetails the UserDetails instance whose status should be checked.
     */
    @Override
    public void validate(UserDetails userDetails) {
        // 账号
        if (Strings.isEmpty(userDetails.getAccessKey())) {
            throw new UserStatusException(ErrorsIMessage.ACCOUNT_NULL);
        }
        // 密码
        if (Strings.isEmpty(userDetails.getSecretKey())) {
            throw new UserStatusException(ErrorsIMessage.ACCOUNT_PD_NULL);
        }

        if (!userDetails.isAccountNonLocked()) {
            throw new UserStatusException(ErrorsIMessage.ACCOUNT_STATUS_LOCKED);
        }
        if (!userDetails.isEnabled()) {
            throw new UserStatusException(ErrorsIMessage.ACCOUNT_STATUS_DISABLED);
        }
        if (!userDetails.isAccountNonExpired()) {
            throw new UserStatusException(ErrorsIMessage.ACCOUNT_STATUS_EXPIRED);
        }
        if (!userDetails.isCredentialsNonExpired()) {
            throw new UserStatusException(ErrorsIMessage.ACCOUNT_STATUS_CREDENTIALS_EXPIRED);
        }
    }
}
