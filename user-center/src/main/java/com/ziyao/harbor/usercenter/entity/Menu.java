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
 * 菜单资源表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 系统id
     */
    private Long appId;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 菜单编码
     */
    private String code;

    /**
     * 资源URL
     */
    private String url;

    /**
     * 资源图标
     */
    private String icon;

    /**
     * 上级资源ID
     */
    private Long parentId;

    /**
     * 资源级别
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新人ID
     */
    private Integer updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;


}
