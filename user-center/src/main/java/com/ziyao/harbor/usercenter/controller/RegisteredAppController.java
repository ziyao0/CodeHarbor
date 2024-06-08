package com.ziyao.harbor.usercenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziyao.harbor.core.error.Exceptions;
import com.ziyao.harbor.usercenter.dto.RegisteredAppDTO;
import com.ziyao.harbor.usercenter.entity.RegisteredApp;
import com.ziyao.harbor.usercenter.service.RegisteredAppService;
import com.ziyao.harbor.web.base.BaseController;
import com.ziyao.harbor.web.base.PageParams;
import com.ziyao.harbor.web.base.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 应用系统 前端控制器
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
@RestController
@RequestMapping("/usercenter/registered-app")
public class RegisteredAppController extends BaseController<RegisteredAppService, RegisteredApp> {

    @Autowired
    private RegisteredAppService registeredAppService;

    @PostMapping("/save")
    public void save(@RequestBody RegisteredAppDTO entityDTO) {
        super.iService.save(entityDTO.getInstance());
    }

    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody RegisteredAppDTO entityDTO) {
        super.iService.saveOrUpdate(entityDTO.getInstance());
    }

    @PostMapping("/updateById")
    public void updateById(@RequestBody RegisteredAppDTO entityDTO) {
        if (ObjectUtils.isEmpty(entityDTO.getId())) {
            throw Exceptions.createIllegalArgumentException(null);
        }
        super.iService.updateById(entityDTO.getInstance());
    }

    /**
     * 默认一次插入500条
     */
    @PostMapping("/saveBatch")
    public void saveBatch(@RequestBody List
            <RegisteredAppDTO> entityDTOList) {
        super.iService.saveBatch(entityDTOList.stream().map(RegisteredAppDTO::getInstance).collect(Collectors.toList()), 500);
    }

    /**
     * 条件分页查询
     *
     * @param pageQuery 分页参数
     * @return 返回分页查询信息
     */
    @PostMapping("/page/get")
    public Page<RegisteredApp> getPage(@RequestBody PageParams<RegisteredAppDTO> pageQuery) {
        Page<RegisteredApp> page = Pages.initPage(pageQuery, RegisteredApp.class);
        return registeredAppService.page(page, pageQuery.getParams());
    }
}
