package com.cfx.usercenter.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
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
 * @since 2023-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统id
     */
    @MppMultiId("APP_ID")
    private Long appId;

    @MppMultiId("USER_ID")
    private Long userId;

    @MppMultiId("ROLE_ID")
    private Long roleId;

    @TableField(value = "CREATED_AT", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private Integer createdBy;


}
