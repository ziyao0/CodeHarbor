package com.ziyao.cfx.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziyao.cfx.usercenter.dto.UserRoleDTO;
import com.ziyao.cfx.usercenter.entity.UserRole;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 分页查询
     */
    Page<UserRole> page(Page<UserRole> page, UserRoleDTO userRoleDTO);
}
