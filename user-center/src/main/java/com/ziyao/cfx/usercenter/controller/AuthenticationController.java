package com.ziyao.cfx.usercenter.controller;

import com.ziyao.cfx.usercenter.dto.LoginDTO;
import com.ziyao.cfx.usercenter.security.api.AccessToken;
import com.ziyao.cfx.usercenter.security.processer.AuthenticationProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationProcessor authenticationProcessor;


    @PostMapping("/login")
    public AccessToken login(@RequestBody LoginDTO loginDTO) {
        // 1 d
        return authenticationProcessor.process(loginDTO);
    }

}
