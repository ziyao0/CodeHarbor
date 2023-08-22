package com.ziyao.harbor.usercenter.security.core;

import com.ziyao.harbor.common.exception.ServiceException;
import com.ziyao.harbor.usercenter.security.api.UserDetails;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public interface UserDetailsChecker {


    /**
     * 检查用户
     *
     * @param check the UserDetails instance whose status should be checked.
     * @throws ServiceException 检查未通过抛出异常
     */
    void check(UserDetails check);

}
