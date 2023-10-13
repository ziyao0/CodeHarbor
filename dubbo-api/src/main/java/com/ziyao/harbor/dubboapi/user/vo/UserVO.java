package com.ziyao.harbor.dubboapi.user.vo;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@Getter
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1654141491584935890L;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
