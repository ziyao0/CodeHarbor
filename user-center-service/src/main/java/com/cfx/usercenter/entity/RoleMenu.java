package com.cfx.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统id
     */
    @TableId("APP_ID")
    private Integer appId;

    /**
     * 角色id
     */
    @TableField("ROLE_ID")
    private Integer roleId;

    /**
     * 菜单id
     */
    @TableField("MENU_ID")
    private Integer menuId;

    /**
     * 创建人id
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATED_AT", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;


}
