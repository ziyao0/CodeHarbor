package com.ziyao.harbor.usercenter.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ziyao.harbor.usercenter.authenticate.core.UserDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户凭证
     */
    private String password;

    /**
     * 账号状态
     */
    private Byte status;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 删除状态 0正常 1 删除
     */
    private Byte deleted;

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


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
