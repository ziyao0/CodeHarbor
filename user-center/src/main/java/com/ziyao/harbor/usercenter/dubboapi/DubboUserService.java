package com.ziyao.harbor.usercenter.dubboapi;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.harbor.dubboapi.user.UserOpenApi;
import com.ziyao.harbor.dubboapi.user.vo.UserVO;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.repository.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@Service
@DubboService(version = "1.0.0")
public class DubboUserService implements UserOpenApi {

    private final UserMapper userMapper;

    public DubboUserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserVO getUser(String username) {

        User user = userMapper.selectOne(
                Wrappers.lambdaQuery(User.class)
                        .eq(User::getUsername, username));

        if (!ObjectUtils.isEmpty(user)) {
            UserVO userVo = new UserVO();
            BeanUtils.copyProperties(user, userVo);
            return userVo;
        }
        return null;
    }
}
