package com.ziyao.harbor.usercenter.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("role")
@Entity(name = "role")
public class Role implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @Id
    @TableId("id")
    private Long id;

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 角色编码
     */
    @TableField("role")
    private String role;

    @TableField("type")
    private Integer type;

    /**
     * 角色类别 1 权限角色 2 组织角色
     */
    @TableField("category")
    private Integer category;

    @TableField("access_level")
    private String accessLevel;

    /**
     * 1 启用 0禁用
     */
    @TableField("active")
    private Boolean active;

    /**
     * 角色描述
     */
    @TableField("description")
    private String description;

    /**
     * 创建人id
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Integer createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改人id
     */
    @TableField(value = "modified_by", fill = FieldFill.UPDATE)
    private Integer modifiedBy;

    /**
     * 修改时间
     */
    @TableField(value = "modified_at", fill = FieldFill.UPDATE)
    private LocalDateTime modifiedAt;


}
