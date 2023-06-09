package com.ziyao.cfx.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.cfx.common.dto.EntityDTO;
import com.ziyao.cfx.usercenter.entity.UserRole;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-09
 */
@Data
public class UserRoleDTO implements EntityDTO<UserRole>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 系统id
     */
    private Long appId;
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
                // 
                .eq(!ObjectUtils.isEmpty(userId), UserRole::getUserId, userId)
                // 
                .eq(!ObjectUtils.isEmpty(roleId), UserRole::getRoleId, roleId)
                ;
    }

    @Override
    public UserRole getEntity() {
        return new UserRole();
    }
}
