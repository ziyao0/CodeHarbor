package com.cfx.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID;主键ID
     */
    @TableId("ID")
    private Integer id;

    /**
     * 系统ID
     */
    @TableField("APP_ID")
    private Integer appId;

    /**
     * 用户账号;账号
     */
    @TableField("ACCESS_KEY")
    private String accessKey;

    /**
     * 昵称;昵称
     */
    @TableField("NICKNAME")
    private String nickname;

    /**
     * 用户凭证;凭证
     */
    @TableField("SECRET_KEY")
    private String secretKey;

    /**
     * 账号状态;账号状态
     */
    @TableField("STATUS")
    private Integer status;

    /**
     * 部门ID
     */
    @TableField("DEPT_ID")
    private Integer deptId;

    /**
     * 部门名称
     */
    @TableField("DEPT_NAME")
    private String deptName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 删除状态 1 正常 0 删除
     */
    @TableField("DELETED")
    @TableLogic
    private Integer deleted;

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
