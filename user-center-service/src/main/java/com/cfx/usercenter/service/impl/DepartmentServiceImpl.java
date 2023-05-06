package com.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.usercenter.dto.DepartmentDTO;
import com.cfx.usercenter.entity.Department;
import com.cfx.usercenter.mapper.DepartmentMapper;
import com.cfx.usercenter.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public Page<Department> page(Page<Department> page, DepartmentDTO departmentDTO) {
        LambdaQueryWrapper<Department> wrapper = initWrapper(departmentDTO);
        return departmentMapper.selectPage(page, wrapper);
    }

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @param departmentDTO 查询条件
     * @see LambdaQueryWrapper
     */
    private LambdaQueryWrapper<Department> initWrapper(DepartmentDTO departmentDTO) {

        LambdaQueryWrapper<Department> wrapper = Wrappers.lambdaQuery(Department.class);
        // 主键id
        wrapper.eq(!StringUtils.isEmpty(departmentDTO.getId()), Department::getId, departmentDTO.getId());
        // 系统id
        wrapper.eq(!StringUtils.isEmpty(departmentDTO.getAppId()), Department::getAppId, departmentDTO.getAppId());
        // 部门名称
        wrapper.eq(!StringUtils.isEmpty(departmentDTO.getDeptName()), Department::getDeptName, departmentDTO.getDeptName());
        // 上级部门id
        wrapper.eq(!StringUtils.isEmpty(departmentDTO.getParentId()), Department::getParentId, departmentDTO.getParentId());
        // 创建人id
        wrapper.eq(!StringUtils.isEmpty(departmentDTO.getCreatedBy()), Department::getCreatedBy, departmentDTO.getCreatedBy());
        // 创建时间
        wrapper.eq(!StringUtils.isEmpty(departmentDTO.getCreatedAt()), Department::getCreatedAt, departmentDTO.getCreatedAt());
        // 修改人id
        wrapper.eq(!StringUtils.isEmpty(departmentDTO.getModifiedBy()), Department::getModifiedBy, departmentDTO.getModifiedBy());
        // 修改时间
        wrapper.eq(!StringUtils.isEmpty(departmentDTO.getModifiedAt()), Department::getModifiedAt, departmentDTO.getModifiedAt());
        return wrapper;
    }
}
