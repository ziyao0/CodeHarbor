package com.cfx.usercenter.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.cfx.web.mvc.BaseController;
import com.cfx.usercenter.service.MenuService;
import com.cfx.usercenter.entity.Menu;

/**
 * <p>
 * 菜单资源表 前端控制器
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-05
 */
@RestController
@RequestMapping("/usercenter/menu")
public class MenuController extends BaseController<MenuService, Menu> {

}
