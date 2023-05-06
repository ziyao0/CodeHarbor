package com.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.usercenter.dto.RoleMenuDTO;
import com.cfx.usercenter.entity.RoleMenu;
import com.cfx.usercenter.mapper.RoleMenuMapper;
import com.cfx.usercenter.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Page<RoleMenu> page(Page<RoleMenu> page, RoleMenuDTO roleMenuDTO) {
        LambdaQueryWrapper<RoleMenu> wrapper = initWrapper(roleMenuDTO);
        return roleMenuMapper.selectPage(page, wrapper);
    }

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @param roleMenuDTO 查询条件
     * @see LambdaQueryWrapper
     */
    private LambdaQueryWrapper<RoleMenu> initWrapper(RoleMenuDTO roleMenuDTO) {

        LambdaQueryWrapper<RoleMenu> wrapper = Wrappers.lambdaQuery(RoleMenu.class);
        // 系统id
        wrapper.eq(!StringUtils.isEmpty(roleMenuDTO.getAppId()), RoleMenu::getAppId, roleMenuDTO.getAppId());
        // 角色id
        wrapper.eq(!StringUtils.isEmpty(roleMenuDTO.getRoleId()), RoleMenu::getRoleId, roleMenuDTO.getRoleId());
        // 菜单id
        wrapper.eq(!StringUtils.isEmpty(roleMenuDTO.getMenuId()), RoleMenu::getMenuId, roleMenuDTO.getMenuId());
        // 创建人id
        wrapper.eq(!StringUtils.isEmpty(roleMenuDTO.getCreatedBy()), RoleMenu::getCreatedBy, roleMenuDTO.getCreatedBy());
        // 创建时间
        wrapper.eq(!StringUtils.isEmpty(roleMenuDTO.getCreatedAt()), RoleMenu::getCreatedAt, roleMenuDTO.getCreatedAt());
        return wrapper;
    }
}
