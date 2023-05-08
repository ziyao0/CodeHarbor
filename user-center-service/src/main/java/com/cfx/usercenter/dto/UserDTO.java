package com.cfx.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cfx.common.dto.EntityDTO;
import com.cfx.usercenter.entity.User;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-08
 */
@Data
public class UserDTO implements EntityDTO<User>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 系统ID
     */
    private Long appId;
    /**
     * 用户账号
     */
    private String accessKey;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户凭证
     */
    private String secretKey;
    /**
     * 账号状态
     */
    private Integer status;
    /**
     * 部门ID
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
    private Integer deleted;
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
    public LambdaQueryWrapper<User> initWrapper() {

        return Wrappers.lambdaQuery(User.class)
                // 系统ID
                .eq(!StringUtils.isEmpty(appId), User::getAppId, appId)
                // 用户账号
                .likeRight(!StringUtils.isEmpty(accessKey), User::getAccessKey, accessKey)
                // 昵称
                .likeRight(!StringUtils.isEmpty(nickname), User::getNickname, nickname)
                // 用户凭证
                .likeRight(!StringUtils.isEmpty(secretKey), User::getSecretKey, secretKey)
                // 账号状态
                .eq(!StringUtils.isEmpty(status), User::getStatus, status)
                // 部门ID
                .eq(!StringUtils.isEmpty(deptId), User::getDeptId, deptId)
                // 部门名称
                .likeRight(!StringUtils.isEmpty(deptName), User::getDeptName, deptName)
                // 排序
                .eq(!StringUtils.isEmpty(sort), User::getSort, sort)
                // 删除状态 0正常 1 删除
                .eq(!StringUtils.isEmpty(deleted), User::getDeleted, deleted)
                // 排序
                .orderByAsc(User::getSort)
                ;
    }

    @Override
    public User getEntity() {
        return new User();
    }
}
