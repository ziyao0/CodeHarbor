package com.cfx.usercenter.controller;


import com.cfx.common.exception.ServiceException;
import com.cfx.common.writer.Errors;
import com.cfx.usercenter.dto.AppDTO;
import com.cfx.usercenter.entity.App;
import com.cfx.usercenter.service.AppService;
import com.cfx.web.mvc.BaseController;
import org.springframework.util.StringUtils;
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
 * @since 2023-05-05
 */
@RestController
@RequestMapping("/usercenter/app")
public class AppController extends BaseController<AppService, App> {

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
        if (StringUtils.isEmpty(entityDTO.getId())) {
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
}
