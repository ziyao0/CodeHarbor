package com.ziyao.harbor.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.harbor.common.dto.EntityDTO;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.entity.Department;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-09
 */
@Data
public class DepartmentDTO implements EntityDTO<Department>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 系统id
     */
    private Long appId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 上级部门id
     */
    private Long parentId;
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
    public LambdaQueryWrapper<Department> initWrapper() {

        return Wrappers.lambdaQuery(Department.class)
                // 系统id
                .eq(!ObjectUtils.isEmpty(appId), Department::getAppId, appId)
                // 部门名称
                .likeRight(Strings.hasLength(deptName), Department::getDeptName, deptName)
                // 上级部门id
                .eq(Objects.nonNull(parentId), Department::getParentId, parentId)
                ;
    }

    @Override
    public Department getEntity() {
        return new Department();
    }
}
