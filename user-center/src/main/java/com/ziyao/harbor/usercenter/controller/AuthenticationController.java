package com.ziyao.harbor.usercenter.controller;

import com.ziyao.harbor.usercenter.authentication.core.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import com.ziyao.harbor.usercenter.service.AuthenticationService;
import com.ziyao.security.oauth2.core.OAuth2AuthorizationCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public String login(@RequestBody AuthenticatedRequest authenticatedRequest) {
        return authenticationService.login(authenticatedRequest);
    }

    @PostMapping("/code/generate")
    public OAuth2AuthorizationCode generateAuthenticationCode(@RequestBody AuthenticationRequest authenticationRequest) {
        try {

            return authenticationService.generateAuthenticationCode(authenticationRequest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
