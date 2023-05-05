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
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("ID")
    private Integer id;

    /**
     * 系统id
     */
    @TableField("APP_ID")
    private Integer appId;

    /**
     * 部门名称
     */
    @TableField("DEPT_NAME")
    private String deptName;

    /**
     * 上级部门id
     */
    @TableField("PARENT_ID")
    private Integer parentId;

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
