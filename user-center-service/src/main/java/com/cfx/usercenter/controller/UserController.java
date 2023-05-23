package com.cfx.usercenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cfx.common.exception.ServiceException;
import com.cfx.common.writer.Errors;
import com.cfx.usercenter.dto.UserDTO;
import com.cfx.usercenter.entity.User;
import com.cfx.usercenter.service.UserService;
import com.cfx.web.context.ContextInfo;
import com.cfx.web.context.ContextManager;
import com.cfx.web.details.UserDetails;
import com.cfx.web.mvc.BaseController;
import com.cfx.web.orm.PageQuery;
import com.cfx.web.orm.PageUtils;
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
            throw new ServiceException(Errors.ILLEGAL_ARGUMENT);
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
     * @param pageQuery 分页参数
     * @return 返回分页查询信息
     */
    @PostMapping("/page/get")
    public Page<User> getPage(@RequestBody PageQuery<UserDTO> pageQuery) {
        Page<User> page = PageUtils.initPage(pageQuery, User.class);
        return userService.page(page, pageQuery.getQuery());
    }

    @GetMapping("/current")
    public UserDetails userDetails() {
        ContextInfo contextInfo = ContextManager.get();
        contextInfo.assertAuthentication();
        return contextInfo.getUser();
    }
}
