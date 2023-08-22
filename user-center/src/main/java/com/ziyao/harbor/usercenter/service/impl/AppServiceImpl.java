package com.ziyao.harbor.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziyao.harbor.usercenter.dto.AppDTO;
import com.ziyao.harbor.usercenter.entity.App;
import com.ziyao.harbor.usercenter.mapper.AppMapper;
import com.ziyao.harbor.usercenter.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 应用系统 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Autowired
    private AppMapper appMapper;

    @Override
    public Page<App> page(Page<App> page, AppDTO appDTO) {
        LambdaQueryWrapper<App> wrapper = appDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return appMapper.selectPage(page, wrapper);
    }
}
