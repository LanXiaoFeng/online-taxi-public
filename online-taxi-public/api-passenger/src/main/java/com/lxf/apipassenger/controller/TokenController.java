package com.lxf.apipassenger.controller;

import com.lxf.apipassenger.service.TokenService;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.response.TokenResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TokenController {

    @Resource
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse){
        String refreshToken = tokenResponse.getRefreshToken();
        System.out.println("原来的 refreshToken："+refreshToken);
        return tokenService.refreshToken(refreshToken);
    }
}
