package com.ziyao.harbor.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziyao.harbor.usercenter.dto.ApplicationDTO;
import com.ziyao.harbor.usercenter.entity.Application;

/**
 * <p>
 * 应用系统 服务类
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
public interface ApplicationService extends IService<Application> {

    /**
     * 分页查询
     */
    Page<Application> page(Page<Application> page, ApplicationDTO applicationDTO);
}
