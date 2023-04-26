package com.cfx.usercenter.service.impl;

import com.cfx.usercenter.entity.User;
import com.cfx.usercenter.mapper.UserMapper;
import com.cfx.usercenter.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-04-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
