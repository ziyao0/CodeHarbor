package com.ziyao.harbor.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziyao.harbor.usercenter.dto.RegisteredAppDTO;
import com.ziyao.harbor.usercenter.entity.RegisteredApp;
import com.ziyao.harbor.usercenter.repository.mapper.RegisteredAppMapper;
import com.ziyao.harbor.usercenter.service.RegisteredAppService;
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
public class RegisteredAppServiceImpl extends ServiceImpl<RegisteredAppMapper, RegisteredApp> implements RegisteredAppService {

    @Autowired
    private RegisteredAppMapper registeredAppMapper;

    @Override
    public Page<RegisteredApp> page(Page<RegisteredApp> page, RegisteredAppDTO registeredAppDTO) {
        LambdaQueryWrapper<RegisteredApp> wrapper = registeredAppDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return registeredAppMapper.selectPage(page, wrapper);
    }
}
