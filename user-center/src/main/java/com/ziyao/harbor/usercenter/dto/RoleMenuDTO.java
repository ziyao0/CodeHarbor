package com.ziyao.harbor.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.harbor.usercenter.entity.RoleMenu;
import com.ziyao.harbor.web.orm.EntityDTO;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
@Data
public class RoleMenuDTO implements EntityDTO<RoleMenu>, Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 系统id
     */
    private Long appId;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 菜单id
     */
    private Long menuId;
    /**
     * 创建人id
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @see LambdaQueryWrapper
     */
    public LambdaQueryWrapper<RoleMenu> initWrapper() {

        return Wrappers.lambdaQuery(RoleMenu.class)
                ;
    }

    @Override
    public RoleMenu getEntity() {
        return new RoleMenu();
    }
}
