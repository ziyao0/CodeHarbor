package com.cfx.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cfx.usercenter.dto.DepartmentDTO;
import com.cfx.usercenter.entity.Department;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 分页查询
     */
    Page<Department> page(Page<Department> page, DepartmentDTO departmentDTO);
}
