package com.ziyao.harbor.dubboapi.user;

import com.ziyao.harbor.dubboapi.user.vo.UserVO;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
public interface UserOpenApi {
    /**
     * 通过系统ID和用户名获取用户信息
     *
     * @param username 用户名
     * @return 返回用户信息
     */
    UserVO getUser(String username);
}
