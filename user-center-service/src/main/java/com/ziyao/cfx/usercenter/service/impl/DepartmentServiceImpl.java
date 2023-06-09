package com.ziyao.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziyao.cfx.usercenter.dto.DepartmentDTO;
import com.ziyao.cfx.usercenter.entity.Department;
import com.ziyao.cfx.usercenter.mapper.DepartmentMapper;
import com.ziyao.cfx.usercenter.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Page<Department> page(Page<Department> page, DepartmentDTO departmentDTO) {
        LambdaQueryWrapper<Department> wrapper = departmentDTO.initWrapper();
        // to do 2023/5/6 默认排序字段 sort/sorted(默认是为ASC)值越小、越往前
        return departmentMapper.selectPage(page, wrapper);
    }
}
