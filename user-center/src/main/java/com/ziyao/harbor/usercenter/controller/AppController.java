package com.ziyao.harbor.usercenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziyao.harbor.usercenter.dto.AppDTO;
import com.ziyao.harbor.usercenter.entity.App;
import com.ziyao.harbor.usercenter.service.AppService;
import com.ziyao.harbor.web.exception.ServiceException;
import com.ziyao.harbor.web.mvc.BaseController;
import com.ziyao.harbor.web.orm.PageQuery;
import com.ziyao.harbor.web.orm.PageUtils;
import com.ziyao.harbor.web.response.Errors;
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
 * @since 2023-05-06
 */
@RestController
@RequestMapping("/usercenter/app")
public class AppController extends BaseController<AppService, App> {

    @Autowired
    private AppService appService;

    @PostMapping("/save")
    public void save(@RequestBody AppDTO entityDTO) {
        super.iService.save(entityDTO.getInstance());
    }

    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody AppDTO entityDTO) {
        super.iService.saveOrUpdate(entityDTO.getInstance());
    }

    @PostMapping("/updateById")
    public void updateById(@RequestBody AppDTO entityDTO) {
        if (ObjectUtils.isEmpty(entityDTO.getId())) {
            throw new ServiceException(Errors.ILLEGAL_ARGUMENT);
        }
        super.iService.updateById(entityDTO.getInstance());
    }

    /**
     * 默认一次插入500条
     */
    @PostMapping("/saveBatch")
    public void saveBatch(@RequestBody List<AppDTO> entityDTOList) {
        super.iService.saveBatch(entityDTOList.stream().map(AppDTO::getInstance).collect(Collectors.toList()), 500);
    }

    /**
     * 条件分页查询
     *
     * @param pageQuery 分页参数
     * @return 返回分页查询信息
     */
    @PostMapping("/page/get")
    public Page<App> getPage(@RequestBody PageQuery<AppDTO> pageQuery) {
        Page<App> page = PageUtils.initPage(pageQuery, App.class);
        return appService.page(page, pageQuery.getQuery());
    }
}
