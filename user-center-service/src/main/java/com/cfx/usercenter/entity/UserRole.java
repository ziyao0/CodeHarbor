package com.cfx.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2023-04-26
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

    @TableField("CREATED_AT")
    private LocalDateTime createdAt;

    @TableField("CREATED_BY")
    private Integer createdBy;


}
