package com.ziyao.cfx.usercenter.security.core;

import com.ziyao.cfx.common.exception.ServiceException;
import com.ziyao.cfx.usercenter.security.api.UserDetails;

/**
 * @author zhangziyao
 * @date 2023/4/24
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
