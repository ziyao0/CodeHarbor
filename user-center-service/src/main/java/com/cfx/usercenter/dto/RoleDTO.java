package com.cfx.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cfx.common.dto.EntityDTO;
import com.cfx.usercenter.entity.Role;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Data
public class RoleDTO implements EntityDTO<Role>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer id;
    /**
     * 系统id
     */
    private Integer appId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 创建人id
     */
    private Integer createdBy;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 修改人id
     */
    private Integer modifiedBy;
    /**
     * 修改时间
     */
    private LocalDateTime modifiedAt;

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @see LambdaQueryWrapper
     */
    public LambdaQueryWrapper<Role> initWrapper() {

        return Wrappers.lambdaQuery(Role.class)
                // 系统id
                .eq(!StringUtils.isEmpty(appId), Role::getAppId, appId)
                // 角色名称
                .likeRight(!StringUtils.isEmpty(roleName), Role::getRoleName, roleName)
                // 角色编码
                .likeRight(!StringUtils.isEmpty(roleCode), Role::getRoleCode, roleCode)
                // 角色描述
                .likeRight(!StringUtils.isEmpty(description), Role::getDescription, description)
                ;
    }

    @Override
    public Role getEntity() {
        return new Role();
    }
}
