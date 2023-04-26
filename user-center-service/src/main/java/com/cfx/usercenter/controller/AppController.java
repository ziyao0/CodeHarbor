package com.cfx.usercenter.controller;


import com.cfx.usercenter.entity.App;
import com.cfx.usercenter.service.AppService;
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
        return appService.list();
    }

}
