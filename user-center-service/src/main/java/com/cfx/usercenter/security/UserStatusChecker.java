package com.cfx.usercenter.security;

import com.cfx.usercenter.comm.exception.ErrorsIMessage;
import com.cfx.usercenter.comm.exception.UserStatusException;
import com.cfx.usercenter.security.api.UserDetails;
import com.cfx.usercenter.security.core.UserDetailsChecker;
import org.springframework.util.ObjectUtils;

/**
 * 检查用户状态公共类
 *
 * @author zhangziyao
 * @date 2023/4/24
 */
public class UserStatusChecker implements UserDetailsChecker {


    /**
     * 检查用户状态
     *
     * @param userDetails the UserDetails instance whose status should be checked.
     */
    @Override
    public void check(UserDetails userDetails) {
        // 账号
        if (ObjectUtils.isEmpty(userDetails.getAccessKey())) {
            throw new UserStatusException(ErrorsIMessage.ACCOUNT_NULL);
        }
        // 密码
        if (ObjectUtils.isEmpty(userDetails.getSecretKey())) {
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
