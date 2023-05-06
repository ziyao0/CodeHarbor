package com.cfx.usercenter.dto;

import com.cfx.common.dto.EntityDTO;
import com.cfx.usercenter.entity.UserRole;
import lombok.Data;

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

    @Override
    public UserRole getEntity() {
        return new UserRole();
    }
}
