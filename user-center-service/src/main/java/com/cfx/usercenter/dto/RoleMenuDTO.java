package com.cfx.usercenter.dto;

import com.cfx.common.dto.EntityDTO;
import com.cfx.usercenter.entity.RoleMenu;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 *角色菜单表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-05
 */
@Data
public class RoleMenuDTO implements EntityDTO<RoleMenu>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统id
     */
    private Integer appId;
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 菜单id
     */
    private Integer menuId;
    /**
     * 创建人id
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    @Override
    public RoleMenu getEntity() {
        return new RoleMenu();
    }
}
