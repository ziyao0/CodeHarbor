package com.ziyao.harbor.usercenter.controllers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziyao.harbor.core.error.Exceptions;
import com.ziyao.harbor.usercenter.dto.UserDTO;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.service.UserService;
import com.ziyao.harbor.web.UserDetails;
import com.ziyao.harbor.web.base.BaseController;
import com.ziyao.harbor.web.base.PageParams;
import com.ziyao.harbor.web.base.Pages;
import com.ziyao.harbor.web.context.ContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@RestController
@RequestMapping("/usercenter/user")
public class UserController extends BaseController<UserService, User> {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public void save(@RequestBody UserDTO entityDTO) {
        super.iService.save(entityDTO.getInstance());
    }

    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody UserDTO entityDTO) {
        super.iService.saveOrUpdate(entityDTO.getInstance());
    }

    @PostMapping("/updateById")
    public void updateById(@RequestBody UserDTO entityDTO) {
        if (ObjectUtils.isEmpty(entityDTO.getId())) {
            throw Exceptions.createIllegalArgumentException(null);
        }
        super.iService.updateById(entityDTO.getInstance());
    }

    /**
     * 默认一次插入500条
     */
    @PostMapping("/saveBatch")
    public void saveBatch(@RequestBody List<UserDTO> entityDTOList) {
        super.iService.saveBatch(entityDTOList.stream().map(UserDTO::getInstance).collect(Collectors.toList()), 500);
    }

    /**
     * 条件分页查询
     *
     * @param pageParams 分页参数
     * @return 返回分页查询信息
     */
    @PostMapping("/page/get")
    public Page<User> getPage(@RequestBody PageParams<UserDTO> pageParams) {
        Page<User> page = Pages.initPage(pageParams, User.class);
        return userService.page(page, pageParams.getParams());
    }

    @GetMapping("/current")
    public UserDetails userDetails() {
        return ContextManager.getUser();
    }
}
