package com.ziyao.harbor.usercenter.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Department implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 系统id
     */
    private Long appId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 上级部门id
     */
    private Long parentId;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改人id
     */
    @TableField(fill = FieldFill.UPDATE)
    private Integer modifiedBy;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime modifiedAt;


}
