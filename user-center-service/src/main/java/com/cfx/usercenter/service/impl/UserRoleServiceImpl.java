package com.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.usercenter.dto.UserRoleDTO;
import com.cfx.usercenter.entity.UserRole;
import com.cfx.usercenter.mapper.UserRoleMapper;
import com.cfx.usercenter.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public Page<UserRole> page(Page<UserRole> page, UserRoleDTO userRoleDTO) {
        LambdaQueryWrapper<UserRole> wrapper = userRoleDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return userRoleMapper.selectPage(page, wrapper);
    }
}