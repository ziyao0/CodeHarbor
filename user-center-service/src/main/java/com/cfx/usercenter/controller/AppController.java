package com.cfx.usercenter.controller;


import com.cfx.common.api.DataIMessage;
import com.cfx.common.writer.ApiResponse;
import com.cfx.usercenter.entity.App;
import com.cfx.usercenter.service.AppService;
import com.cfx.web.utils.ApplicationContextUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 应用系统 前端控制器
 * </p>
 *
 * @author zhangziyao
 * @since 2023-04-26
 */
@RestController
@RequestMapping("/usercenter/app")
public class AppController {


    @Resource
    private AppService appService;

    @PostMapping("list")
    public List<App> list() {
        ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContext();
        return appService.list();
    }

    @PostMapping("list2")
    public ApiResponse<List<App>> list2() {
        return ApiResponse.success(appService.list());
    }

}
