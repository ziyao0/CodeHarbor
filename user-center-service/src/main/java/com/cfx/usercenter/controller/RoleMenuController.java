package com.cfx.usercenter.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.cfx.web.mvc.BaseController;
import com.cfx.usercenter.service.RoleMenuService;
import com.cfx.usercenter.entity.RoleMenu;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-05
 */
@RestController
@RequestMapping("/usercenter/role-menu")
public class RoleMenuController extends BaseController<RoleMenuService, RoleMenu> {

}
