package com.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.usercenter.dto.AppDTO;
import com.cfx.usercenter.entity.App;
import com.cfx.usercenter.mapper.AppMapper;
import com.cfx.usercenter.service.AppService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        LambdaQueryWrapper<App> wrapper = initWrapper(appDTO);
        return appMapper.selectPage(page, wrapper);
    }

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @param appDTO 查询条件
     * @see LambdaQueryWrapper
     */
    private LambdaQueryWrapper<App> initWrapper(AppDTO appDTO) {

        LambdaQueryWrapper<App> wrapper = Wrappers.lambdaQuery(App.class);
        // 主键id
        wrapper.eq(!StringUtils.isEmpty(appDTO.getId()), App::getId, appDTO.getId());
        // 系统名称
        wrapper.eq(!StringUtils.isEmpty(appDTO.getAppName()), App::getAppName, appDTO.getAppName());
        // 系统访问路径
        wrapper.eq(!StringUtils.isEmpty(appDTO.getUrl()), App::getUrl, appDTO.getUrl());
        // 系统介绍
        wrapper.eq(!StringUtils.isEmpty(appDTO.getIntroduce()), App::getIntroduce, appDTO.getIntroduce());
        // 删除状态 0正常 1 删除
        wrapper.eq(!StringUtils.isEmpty(appDTO.getDeleted()), App::getDeleted, appDTO.getDeleted());
        // 创建人id
        wrapper.eq(!StringUtils.isEmpty(appDTO.getCreatedBy()), App::getCreatedBy, appDTO.getCreatedBy());
        // 创建时间
        wrapper.eq(!StringUtils.isEmpty(appDTO.getCreatedAt()), App::getCreatedAt, appDTO.getCreatedAt());
        // 修改人id
        wrapper.eq(!StringUtils.isEmpty(appDTO.getModifiedBy()), App::getModifiedBy, appDTO.getModifiedBy());
        // 修改时间
        wrapper.eq(!StringUtils.isEmpty(appDTO.getModifiedAt()), App::getModifiedAt, appDTO.getModifiedAt());
        return wrapper;
    }
}
