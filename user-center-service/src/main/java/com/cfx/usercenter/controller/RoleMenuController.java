package com.cfx.usercenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cfx.common.exception.ServiceException;
import com.cfx.common.writer.Errors;
import com.cfx.usercenter.dto.RoleMenuDTO;
import com.cfx.usercenter.entity.RoleMenu;
import com.cfx.usercenter.service.RoleMenuService;
import com.cfx.web.mvc.BaseController;
import com.cfx.web.orm.PageQuery;
import com.cfx.web.orm.PageUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单表 前端控制器
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@RestController
@RequestMapping("/usercenter/role-menu")
public class RoleMenuController extends BaseController<RoleMenuService, RoleMenu> {

    @Resource
    private RoleMenuService roleMenuService;

    @PostMapping("/save")
    public void save(@RequestBody RoleMenuDTO entityDTO) {
        super.iService.save(entityDTO.getInstance());
    }

    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody RoleMenuDTO entityDTO) {
        super.iService.saveOrUpdate(entityDTO.getInstance());
    }

    @PostMapping("/updateById")
    public void updateById(@RequestBody RoleMenuDTO entityDTO) {
        if (StringUtils.isEmpty(entityDTO.getId())) {
            throw new ServiceException(Errors.ILLEGAL_ARGUMENT);
        }
        super.iService.updateById(entityDTO.getInstance());
    }

    /**
     * 默认一次插入500条
     */
    @PostMapping("/saveBatch")
    public void saveBatch(@RequestBody List<RoleMenuDTO> entityDTOList) {
        super.iService.saveBatch(entityDTOList.stream().map(RoleMenuDTO::getInstance).collect(Collectors.toList()), 500);
    }

    /**
     * 条件分页查询
     *
     * @param pageQuery 分页参数
     * @return 返回分页查询信息
     */
    @PostMapping("/page/get")
    public Page<RoleMenu> getPage(@RequestBody PageQuery<RoleMenuDTO> pageQuery) {
        Page<RoleMenu> page = PageUtils.initPage(pageQuery, RoleMenu.class);
        return roleMenuService.page(page, pageQuery.getQuery());
    }
}