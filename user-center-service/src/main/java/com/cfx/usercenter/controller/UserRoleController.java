package com.cfx.usercenter.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.cfx.web.mvc.BaseController;
import com.cfx.usercenter.service.UserRoleService;
import com.cfx.usercenter.entity.UserRole;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-05
 */
@RestController
@RequestMapping("/usercenter/user-role")
public class UserRoleController extends BaseController<UserRoleService, UserRole> {

}
