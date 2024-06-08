package com.ziyao.harbor.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.harbor.usercenter.entity.UserRole;
import com.ziyao.harbor.web.orm.EntityDTO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
@Data
public class UserRoleDTO implements EntityDTO<UserRole>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Long userId;
    /**
     * 
     */
    private Long roleId;
    /**
     * 
     */
    private LocalDateTime createdAt;
    /**
     * 
     */
    private Integer createdBy;

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @see LambdaQueryWrapper
     */
    public LambdaQueryWrapper<UserRole> initWrapper() {

        return Wrappers.lambdaQuery(UserRole.class)
                ;
    }

    @Override
    public UserRole getEntity() {
        return new UserRole();
    }
}
