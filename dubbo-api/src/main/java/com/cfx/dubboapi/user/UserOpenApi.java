package com.cfx.dubboapi.user;

import com.cfx.dubboapi.user.vo.UserVO;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public interface UserOpenApi {
    /**
     * 通过系统ID和用户名获取用户信息
     *
     * @param appid    系统ID
     * @param username 用户名
     * @return 返回用户信息
     */
    UserVO getUser(Long appid, String username);
}
