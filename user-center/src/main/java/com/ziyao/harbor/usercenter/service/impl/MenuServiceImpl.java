package com.ziyao.harbor.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziyao.harbor.usercenter.dto.MenuDTO;
import com.ziyao.harbor.usercenter.entity.Menu;
import com.ziyao.harbor.usercenter.repository.mapper.MenuMapper;
import com.ziyao.harbor.usercenter.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Page<Menu> page(Page<Menu> page, MenuDTO menuDTO) {
        LambdaQueryWrapper<Menu> wrapper = menuDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return menuMapper.selectPage(page, wrapper);
    }
}
