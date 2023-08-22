package com.ziyao.harbor.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziyao.harbor.usercenter.dto.AppDTO;
import com.ziyao.harbor.usercenter.entity.App;

/**
 * <p>
 * 应用系统 服务类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
public interface AppService extends IService<App> {

    /**
     * 分页查询
     */
    Page<App> page(Page<App> page, AppDTO appDTO);
}
