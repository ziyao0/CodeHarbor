package com.ziyao.harbor.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziyao.harbor.usercenter.dto.Oauth2AuthorizationDTO;
import com.ziyao.harbor.usercenter.entity.Oauth2Authorization;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
public interface Oauth2AuthorizationService extends IService<Oauth2Authorization> {

    /**
     * 分页查询
     */
    Page<Oauth2Authorization> page(Page<Oauth2Authorization> page, Oauth2AuthorizationDTO oauth2AuthorizationDTO);
}
