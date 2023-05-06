package com.cfx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cfx.usercenter.dto.UserDTO;
import com.cfx.usercenter.entity.User;
import com.cfx.usercenter.mapper.UserMapper;
import com.cfx.usercenter.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Page<User> page(Page<User> page, UserDTO userDTO) {
        LambdaQueryWrapper<User> wrapper = initWrapper(userDTO);
        return userMapper.selectPage(page, wrapper);
    }

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @param userDTO 查询条件
     * @see LambdaQueryWrapper
     */
    private LambdaQueryWrapper<User> initWrapper(UserDTO userDTO) {

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
        // 用户ID
        wrapper.eq(!StringUtils.isEmpty(userDTO.getId()), User::getId, userDTO.getId());
        // 系统ID
        wrapper.eq(!StringUtils.isEmpty(userDTO.getAppId()), User::getAppId, userDTO.getAppId());
        // 用户账号
        wrapper.eq(!StringUtils.isEmpty(userDTO.getAccessKey()), User::getAccessKey, userDTO.getAccessKey());
        // 昵称
        wrapper.eq(!StringUtils.isEmpty(userDTO.getNickname()), User::getNickname, userDTO.getNickname());
        // 用户凭证
        wrapper.eq(!StringUtils.isEmpty(userDTO.getSecretKey()), User::getSecretKey, userDTO.getSecretKey());
        // 账号状态
        wrapper.eq(!StringUtils.isEmpty(userDTO.getStatus()), User::getStatus, userDTO.getStatus());
        // 部门ID
        wrapper.eq(!StringUtils.isEmpty(userDTO.getDeptId()), User::getDeptId, userDTO.getDeptId());
        // 部门名称
        wrapper.eq(!StringUtils.isEmpty(userDTO.getDeptName()), User::getDeptName, userDTO.getDeptName());
        // 排序
        wrapper.eq(!StringUtils.isEmpty(userDTO.getSort()), User::getSort, userDTO.getSort());
        // 删除状态 0正常 1 删除
        wrapper.eq(!StringUtils.isEmpty(userDTO.getDeleted()), User::getDeleted, userDTO.getDeleted());
        // 创建人id
        wrapper.eq(!StringUtils.isEmpty(userDTO.getCreatedBy()), User::getCreatedBy, userDTO.getCreatedBy());
        // 创建时间
        wrapper.eq(!StringUtils.isEmpty(userDTO.getCreatedAt()), User::getCreatedAt, userDTO.getCreatedAt());
        // 修改人id 
        wrapper.eq(!StringUtils.isEmpty(userDTO.getModifiedBy()), User::getModifiedBy, userDTO.getModifiedBy());
        // 修改时间
        wrapper.eq(!StringUtils.isEmpty(userDTO.getModifiedAt()), User::getModifiedAt, userDTO.getModifiedAt());
        return wrapper;
    }
}
