package com.ziyao.harbor.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziyao.harbor.usercenter.dto.ApplicationDTO;
import com.ziyao.harbor.usercenter.entity.Application;
import com.ziyao.harbor.usercenter.mapper.ApplicationMapper;
import com.ziyao.harbor.usercenter.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用系统 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public Page<Application> page(Page<Application> page, ApplicationDTO applicationDTO) {
        LambdaQueryWrapper<Application> wrapper = applicationDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return applicationMapper.selectPage(page, wrapper);
    }
}
