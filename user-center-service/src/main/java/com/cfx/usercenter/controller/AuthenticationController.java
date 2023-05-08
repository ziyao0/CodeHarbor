package com.cfx.usercenter.controller;

import com.cfx.common.writer.ApiResponse;
import com.cfx.usercenter.dto.LoginDTO;
import com.cfx.usercenter.security.api.Authentication;
import com.cfx.usercenter.security.processer.AuthenticationProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eason
 * @since 2023/5/8
 */
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Resource
    private HttpServletResponse response;
    @Resource
    private AuthenticationProcessor authenticationProcessor;


    @PostMapping("/login")
    public ApiResponse<Authentication> login(@RequestBody LoginDTO loginDTO) {
        // 1 d
        Authentication process = authenticationProcessor.process(loginDTO);
        response.setHeader("Authorization", "Bearer " + "token");
        return null;
    }


}
