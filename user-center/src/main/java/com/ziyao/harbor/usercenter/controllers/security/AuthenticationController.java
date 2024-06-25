package com.ziyao.harbor.usercenter.controllers.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziyao
 * @since 2024/06/19 09:08:25
 */
@RestController
@RequestMapping("/sso")
public class AuthenticationController {


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("verify")
    public String verify() {
        return "verify";
    }

}
