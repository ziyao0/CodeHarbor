package com.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.usercenter.dto.MenuDTO;
import com.cfx.usercenter.entity.Menu;
import com.cfx.usercenter.mapper.MenuMapper;
import com.cfx.usercenter.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 菜单资源表 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public Page<Menu> page(Page<Menu> page, MenuDTO menuDTO) {
        LambdaQueryWrapper<Menu> wrapper = initWrapper(menuDTO);
        return menuMapper.selectPage(page, wrapper);
    }

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @param menuDTO 查询条件
     * @see LambdaQueryWrapper
     */
    private LambdaQueryWrapper<Menu> initWrapper(MenuDTO menuDTO) {

        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery(Menu.class);
        // 资源ID
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getId()), Menu::getId, menuDTO.getId());
        // 系统id
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getAppId()), Menu::getAppId, menuDTO.getAppId());
        // 资源名称
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getName()), Menu::getName, menuDTO.getName());
        // 菜单编码
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getCode()), Menu::getCode, menuDTO.getCode());
        // 资源URL
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getUrl()), Menu::getUrl, menuDTO.getUrl());
        // 资源图标
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getIcon()), Menu::getIcon, menuDTO.getIcon());
        // 上级资源ID
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getParentId()), Menu::getParentId, menuDTO.getParentId());
        // 资源级别
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getLevel()), Menu::getLevel, menuDTO.getLevel());
        // 排序
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getSort()), Menu::getSort, menuDTO.getSort());
        // 创建人ID
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getCreatedBy()), Menu::getCreatedBy, menuDTO.getCreatedBy());
        // 创建时间
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getCreatedAt()), Menu::getCreatedAt, menuDTO.getCreatedAt());
        // 更新人ID
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getUpdatedBy()), Menu::getUpdatedBy, menuDTO.getUpdatedBy());
        // 更新时间
        wrapper.eq(!StringUtils.isEmpty(menuDTO.getUpdatedAt()), Menu::getUpdatedAt, menuDTO.getUpdatedAt());
        return wrapper;
    }
}
