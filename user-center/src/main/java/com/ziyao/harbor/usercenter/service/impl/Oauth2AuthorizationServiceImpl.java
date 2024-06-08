package com.ziyao.harbor.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziyao.harbor.usercenter.dto.Oauth2AuthorizationDTO;
import com.ziyao.harbor.usercenter.entity.Oauth2Authorization;
import com.ziyao.harbor.usercenter.repository.mapper.Oauth2AuthorizationMapper;
import com.ziyao.harbor.usercenter.service.Oauth2AuthorizationService;
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
public class Oauth2AuthorizationServiceImpl extends ServiceImpl<Oauth2AuthorizationMapper, Oauth2Authorization> implements Oauth2AuthorizationService {

    @Autowired
    private Oauth2AuthorizationMapper oauth2AuthorizationMapper;

    @Override
    public Page<Oauth2Authorization> page(Page<Oauth2Authorization> page, Oauth2AuthorizationDTO oauth2AuthorizationDTO) {
        LambdaQueryWrapper<Oauth2Authorization> wrapper = oauth2AuthorizationDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return oauth2AuthorizationMapper.selectPage(page, wrapper);
    }
}
