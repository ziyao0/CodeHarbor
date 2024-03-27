package com.ziyao.harbor.usercenter.authenticate.support;

import com.ziyao.harbor.usercenter.authenticate.core.UserDetails;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public interface UserDetailsValidator {


    /**
     * 检查用户
     *
     * @param check the UserDetails instance whose status should be checked.
     * @throws com.ziyao.harbor.web.exception.ServiceException 检查未通过抛出异常
     */
    void validate(UserDetails check);

}
