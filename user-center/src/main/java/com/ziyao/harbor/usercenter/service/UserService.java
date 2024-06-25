package com.ziyao.harbor.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziyao.harbor.usercenter.dto.UserDTO;
import com.ziyao.harbor.usercenter.entity.User;

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
     * @param username 用户登陆凭证
     * @return 返回用户信息
     */
    User loadUserDetails(String username);
}
