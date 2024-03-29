package com.ziyao.harbor.usercenter.controller;

import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.service.AuthenticatedService;
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
public class AuthenticatedController {

    @Autowired
    private AuthenticatedService authenticatedService;


    @PostMapping("/login")
    public String login(@RequestBody AuthenticatedRequest authenticatedRequest) {
        return authenticatedService.login(authenticatedRequest);
    }

}
