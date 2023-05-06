package com.cfx.usercenter.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统id
     */
    @TableId("APP_ID")
    private Integer appId;

    @TableField("USER_ID")
    private Integer userId;

    @TableField("ROLE_ID")
    private Integer roleId;

    @TableField(value = "CREATED_AT", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private Integer createdBy;


}
