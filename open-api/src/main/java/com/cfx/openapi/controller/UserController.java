package com.cfx.openapi.controller;

import com.cfx.dubboapi.user.UserOpenApi;
import com.cfx.dubboapi.user.vo.UserVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eason
 * @since 2023/5/15
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @DubboReference(version = "1.0.0")
    private UserOpenApi userOpenApi;

    @GetMapping("/{appid}/{username}")
    public UserVO getUserByAppidAndUserId(@PathVariable("appid") Long appid, @PathVariable("username") String username) {

        return userOpenApi.getUser(appid, username);
    }
}
