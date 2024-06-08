package com.ziyao.harbor.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziyao.harbor.usercenter.dto.RegisteredAppDTO;
import com.ziyao.harbor.usercenter.entity.RegisteredApp;

/**
 * <p>
 * 应用系统 服务类
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
public interface RegisteredAppService extends IService<RegisteredApp> {

    /**
     * 分页查询
     */
    Page<RegisteredApp> page(Page<RegisteredApp> page, RegisteredAppDTO registeredAppDTO);
}
