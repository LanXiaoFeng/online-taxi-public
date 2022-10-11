package com.lxf.apipassenger.controller;

import com.lxf.apipassenger.service.UserService;
import com.lxf.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/users")
    public ResponseResult getUser(HttpServletRequest request){
        // 从http请求中获取accessToken
        // 11
        String authorization = request.getHeader("Authorization");
        return userService.getUserByAccessToken(authorization);
    }
}
