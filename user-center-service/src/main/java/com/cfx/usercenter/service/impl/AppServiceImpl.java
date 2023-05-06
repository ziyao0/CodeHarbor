package com.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.usercenter.dto.AppDTO;
import com.cfx.usercenter.entity.App;
import com.cfx.usercenter.mapper.AppMapper;
import com.cfx.usercenter.service.AppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource
    private AppMapper appMapper;

    @Override
    public Page<App> page(Page<App> page, AppDTO appDTO) {
        LambdaQueryWrapper<App> wrapper = appDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return appMapper.selectPage(page, wrapper);
    }
}
