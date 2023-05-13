package com.cfx.usercenter.dubboapi;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cfx.dubboapi.user.UserOpenApi;
import com.cfx.dubboapi.user.vo.UserVo;
import com.cfx.usercenter.entity.User;
import com.cfx.usercenter.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@Service
@DubboService(version = "1.0.0")
public class DubboUserService implements UserOpenApi {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVo getUser(Long appid, String username) {

        User user = userMapper.selectOne(
                Wrappers.lambdaQuery(User.class)
                        .eq(User::getAppId, appid)
                        .eq(User::getAccessKey, username));

        if (!ObjectUtils.isEmpty(user)) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            return userVo;
        }
        return null;
    }
}
