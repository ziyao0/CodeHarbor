package com.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.usercenter.dto.UserDTO;
import com.cfx.usercenter.entity.App;
import com.cfx.usercenter.entity.User;
import com.cfx.usercenter.mapper.UserMapper;
import com.cfx.usercenter.service.UserService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Page<User> page(Page<User> page, UserDTO userDTO) {
        LambdaQueryWrapper<User> wrapper = userDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return userMapper.selectPage(page, wrapper);
    }

    @Override
    public User loadUserDetails(String appid, String accessKey) {
        // 通过appid和accessKey获取用户信息
        return userMapper.selectOne(
                Wrappers.lambdaQuery(User.class)
                        .eq(User::getAppId, appid)
                        .eq(User::getAccessKey, accessKey));

    }
}
