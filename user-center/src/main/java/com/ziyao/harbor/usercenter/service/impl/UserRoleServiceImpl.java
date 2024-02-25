package com.ziyao.harbor.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziyao.harbor.usercenter.dto.UserRoleDTO;
import com.ziyao.harbor.usercenter.entity.UserRole;
import com.ziyao.harbor.usercenter.repository.mapper.UserRoleMapper;
import com.ziyao.harbor.usercenter.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Page<UserRole> page(Page<UserRole> page, UserRoleDTO userRoleDTO) {
        LambdaQueryWrapper<UserRole> wrapper = userRoleDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return userRoleMapper.selectPage(page, wrapper);
    }
}
