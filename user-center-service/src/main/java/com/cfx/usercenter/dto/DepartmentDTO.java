package com.cfx.usercenter.dto;

import com.cfx.common.dto.EntityDTO;
import com.cfx.usercenter.entity.Department;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 *部门表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Data
public class DepartmentDTO implements EntityDTO<Department>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 系统id
     */
    private Integer appId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 上级部门id
     */
    private Integer parentId;
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
    public Department getEntity() {
        return new Department();
    }
}
