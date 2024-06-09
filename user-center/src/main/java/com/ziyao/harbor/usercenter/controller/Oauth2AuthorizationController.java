package com.ziyao.harbor.usercenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziyao.harbor.core.error.Exceptions;
import com.ziyao.harbor.usercenter.dto.Oauth2AuthorizationDTO;
import com.ziyao.harbor.usercenter.entity.Authorization;
import com.ziyao.harbor.usercenter.service.AuthorizationService;
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
 * 前端控制器
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
@RestController
@RequestMapping("/usercenter/oauth2-authorization")
public class Oauth2AuthorizationController extends BaseController<AuthorizationService, Authorization> {

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/save")
    public void save(@RequestBody Oauth2AuthorizationDTO entityDTO) {
        super.iService.save(entityDTO.getInstance());
    }

    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody Oauth2AuthorizationDTO entityDTO) {
        super.iService.saveOrUpdate(entityDTO.getInstance());
    }

    @PostMapping("/updateById")
    public void updateById(@RequestBody Oauth2AuthorizationDTO entityDTO) {
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
            <Oauth2AuthorizationDTO> entityDTOList) {
        super.iService.saveBatch(entityDTOList.stream().map(Oauth2AuthorizationDTO::getInstance).collect(Collectors.toList()), 500);
    }

    /**
     * 条件分页查询
     *
     * @param pageQuery 分页参数
     * @return 返回分页查询信息
     */
    @PostMapping("/page/get")
    public Page<Authorization> getPage(@RequestBody PageParams<Oauth2AuthorizationDTO> pageQuery) {
        Page<Authorization> page = Pages.initPage(pageQuery, Authorization.class);
        return authorizationService.page(page, pageQuery.getParams());
    }
}
