package com.cfx.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cfx.usercenter.dto.UserDTO;
import com.cfx.usercenter.entity.User;
import com.cfx.usercenter.security.api.UserDetails;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
public interface UserService extends IService<User> {

    /**
     * 分页查询
     */
    Page<User> page(Page<User> page, UserDTO userDTO);

    /**
     * 获取用户信息
     *
     * @param appid     系统ID
     * @param accessKey 用户登陆凭证
     * @return 返回用户信息
     */
    User loadUserDetails(String appid, String accessKey);
}
