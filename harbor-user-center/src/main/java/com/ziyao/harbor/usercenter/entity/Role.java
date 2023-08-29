package com.ziyao.harbor.usercenter.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 系统id
     */
    @TableField("APP_ID")
    private Long appId;

    /**
     * 角色名称
     */
    @TableField("ROLE_NAME")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField("ROLE_CODE")
    private String roleCode;

    /**
     * 角色描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 创建人id
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private Integer createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATED_AT", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改人id
     */
    @TableField(value = "MODIFIED_BY", fill = FieldFill.UPDATE)
    private Integer modifiedBy;

    /**
     * 修改时间
     */
    @TableField(value = "MODIFIED_AT", fill = FieldFill.UPDATE)
    private LocalDateTime modifiedAt;


}
