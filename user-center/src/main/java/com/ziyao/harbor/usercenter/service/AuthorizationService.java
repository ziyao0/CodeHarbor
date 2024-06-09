package com.ziyao.harbor.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziyao.harbor.usercenter.dto.Oauth2AuthorizationDTO;
import com.ziyao.harbor.usercenter.entity.Authorization;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
public interface AuthorizationService extends IService<Authorization> {

    /**
     * 分页查询
     */
    Page<Authorization> page(Page<Authorization> page, Oauth2AuthorizationDTO oauth2AuthorizationDTO);
}
