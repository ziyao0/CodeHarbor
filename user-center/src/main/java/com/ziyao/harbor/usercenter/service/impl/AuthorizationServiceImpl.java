package com.ziyao.harbor.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziyao.harbor.usercenter.dto.AuthorizationDTO;
import com.ziyao.harbor.usercenter.entity.Authorization;
import com.ziyao.harbor.usercenter.repository.mapper.AuthorizationMapper;
import com.ziyao.harbor.usercenter.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
@Service
public class AuthorizationServiceImpl extends ServiceImpl<AuthorizationMapper, Authorization> implements AuthorizationService {

    @Autowired
    private AuthorizationMapper authorizationMapper;

    @Override
    public Page<Authorization> page(Page<Authorization> page, AuthorizationDTO authorizationDTO) {
        LambdaQueryWrapper<Authorization> wrapper = authorizationDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return authorizationMapper.selectPage(page, wrapper);
    }
}
