package com.ziyao.cfx.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziyao.cfx.usercenter.dto.MenuDTO;
import com.ziyao.cfx.usercenter.entity.Menu;

/**
 * <p>
 * 菜单资源表 服务类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
public interface MenuService extends IService<Menu> {

    /**
     * 分页查询
     */
    Page<Menu> page(Page<Menu> page, MenuDTO menuDTO);
}
