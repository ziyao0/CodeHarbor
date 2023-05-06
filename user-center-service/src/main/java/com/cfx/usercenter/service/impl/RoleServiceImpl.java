package com.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.usercenter.dto.RoleDTO;
import com.cfx.usercenter.entity.Role;
import com.cfx.usercenter.mapper.RoleMapper;
import com.cfx.usercenter.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public Page<Role> page(Page<Role> page, RoleDTO roleDTO) {
        LambdaQueryWrapper<Role> wrapper = initWrapper(roleDTO);
        return roleMapper.selectPage(page, wrapper);
    }

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @param roleDTO 查询条件
     * @see LambdaQueryWrapper
     */
    private LambdaQueryWrapper<Role> initWrapper(RoleDTO roleDTO) {

        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery(Role.class);
        // 角色ID
        wrapper.eq(!StringUtils.isEmpty(roleDTO.getId()), Role::getId, roleDTO.getId());
        // 系统id
        wrapper.eq(!StringUtils.isEmpty(roleDTO.getAppId()), Role::getAppId, roleDTO.getAppId());
        // 角色名称
        wrapper.eq(!StringUtils.isEmpty(roleDTO.getRoleName()), Role::getRoleName, roleDTO.getRoleName());
        // 角色编码
        wrapper.eq(!StringUtils.isEmpty(roleDTO.getRoleCode()), Role::getRoleCode, roleDTO.getRoleCode());
        // 角色描述
        wrapper.eq(!StringUtils.isEmpty(roleDTO.getDescription()), Role::getDescription, roleDTO.getDescription());
        // 创建人id
        wrapper.eq(!StringUtils.isEmpty(roleDTO.getCreatedBy()), Role::getCreatedBy, roleDTO.getCreatedBy());
        // 创建时间
        wrapper.eq(!StringUtils.isEmpty(roleDTO.getCreatedAt()), Role::getCreatedAt, roleDTO.getCreatedAt());
        // 修改人id
        wrapper.eq(!StringUtils.isEmpty(roleDTO.getModifiedBy()), Role::getModifiedBy, roleDTO.getModifiedBy());
        // 修改时间
        wrapper.eq(!StringUtils.isEmpty(roleDTO.getModifiedAt()), Role::getModifiedAt, roleDTO.getModifiedAt());
        return wrapper;
    }
}
