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
 * 部门表
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("department")
@Entity(name = "department")
public class Department implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @TableId("id")
    private Long id;

    /**
     * 系统id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 上级部门id
     */
    @TableField("parent_id")
    private Long parentId;

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
