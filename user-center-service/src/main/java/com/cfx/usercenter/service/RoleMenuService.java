package com.cfx.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cfx.usercenter.dto.RoleMenuDTO;
import com.cfx.usercenter.entity.RoleMenu;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 分页查询
     */
    Page<RoleMenu> page(Page<RoleMenu> page, RoleMenuDTO roleMenuDTO);
}
