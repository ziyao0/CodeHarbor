package com.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.usercenter.dto.MenuDTO;
import com.cfx.usercenter.entity.Menu;
import com.cfx.usercenter.mapper.MenuMapper;
import com.cfx.usercenter.service.MenuService;
import org.springframework.stereotype.Service;

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
        LambdaQueryWrapper<Menu> wrapper = menuDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return menuMapper.selectPage(page, wrapper);
    }
}
