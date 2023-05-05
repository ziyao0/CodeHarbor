package com.cfx.usercenter.dto;

import com.cfx.common.dto.EntityDTO;
import com.cfx.usercenter.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 *用户表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-05
 */
@Data
public class UserDTO implements EntityDTO<User>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 系统ID
     */
    private Integer appId;
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
    private Integer deptId;
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

    @Override
    public User getEntity() {
        return new User();
    }
}
