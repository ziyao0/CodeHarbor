package com.ziyao.harbor.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.entity.Role;
import com.ziyao.harbor.web.orm.EntityDTO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-09
 */
@Data
public class RoleDTO implements EntityDTO<Role>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long id;
    /**
     * 系统id
     */
    private Long appId;
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
                .eq(Objects.nonNull(appId), Role::getAppId, appId)
                // 角色名称
                .likeRight(Strings.hasLength(roleName), Role::getRoleName, roleName)
                // 角色编码
                .likeRight(Strings.hasLength(roleCode), Role::getRoleCode, roleCode)
                // 角色描述
                .likeRight(Strings.hasLength(description), Role::getDescription, description)
                ;
    }

    @Override
    public Role getEntity() {
        return new Role();
    }
}
