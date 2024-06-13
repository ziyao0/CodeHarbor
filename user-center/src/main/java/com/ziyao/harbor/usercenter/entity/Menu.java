package com.ziyao.harbor.usercenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单资源表
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("menu")
@Entity(name = "menu")
public class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 系统id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 资源名称
     */
    @TableField("name")
    private String name;

    /**
     * 菜单编码
     */
    @TableField("code")
    private String code;

    /**
     * 资源URL
     */
    @TableField("url")
    private String url;

    /**
     * 资源图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 上级资源ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 资源级别
     */
    @TableField("level")
    private Byte level;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 创建人ID
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Integer createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新人ID
     */
    @TableField("updated_by")
    private Integer updatedBy;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;


}
