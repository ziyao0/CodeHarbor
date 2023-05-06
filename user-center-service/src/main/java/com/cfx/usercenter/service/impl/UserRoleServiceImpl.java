package com.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.usercenter.dto.UserRoleDTO;
import com.cfx.usercenter.entity.UserRole;
import com.cfx.usercenter.mapper.UserRoleMapper;
import com.cfx.usercenter.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        LambdaQueryWrapper<UserRole> wrapper = initWrapper(userRoleDTO);
        return userRoleMapper.selectPage(page, wrapper);
    }

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @param userRoleDTO 查询条件
     * @see LambdaQueryWrapper
     */
    private LambdaQueryWrapper<UserRole> initWrapper(UserRoleDTO userRoleDTO) {

        LambdaQueryWrapper<UserRole> wrapper = Wrappers.lambdaQuery(UserRole.class);
        // 系统id
        wrapper.eq(!StringUtils.isEmpty(userRoleDTO.getAppId()), UserRole::getAppId, userRoleDTO.getAppId());
        // 
        wrapper.eq(!StringUtils.isEmpty(userRoleDTO.getUserId()), UserRole::getUserId, userRoleDTO.getUserId());
        // 
        wrapper.eq(!StringUtils.isEmpty(userRoleDTO.getRoleId()), UserRole::getRoleId, userRoleDTO.getRoleId());
        // 
        wrapper.eq(!StringUtils.isEmpty(userRoleDTO.getCreatedAt()), UserRole::getCreatedAt, userRoleDTO.getCreatedAt());
        // 
        wrapper.eq(!StringUtils.isEmpty(userRoleDTO.getCreatedBy()), UserRole::getCreatedBy, userRoleDTO.getCreatedBy());
        return wrapper;
    }
}
