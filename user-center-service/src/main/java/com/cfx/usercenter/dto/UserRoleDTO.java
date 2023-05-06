package com.cfx.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cfx.common.dto.EntityDTO;
import com.cfx.usercenter.entity.UserRole;
import lombok.Data;
import org.springframework.util.StringUtils;

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
public class UserRoleDTO implements EntityDTO<UserRole>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统id
     */
    private Integer appId;
    /**
     * 
     */
    private Integer userId;
    /**
     * 
     */
    private Integer roleId;
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
                .eq(!StringUtils.isEmpty(userId), UserRole::getUserId, userId)
                // 
                .eq(!StringUtils.isEmpty(roleId), UserRole::getRoleId, roleId)
                ;
    }

    @Override
    public UserRole getEntity() {
        return new UserRole();
    }
}
