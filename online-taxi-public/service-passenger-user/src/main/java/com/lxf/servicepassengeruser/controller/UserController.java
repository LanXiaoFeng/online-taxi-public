package com.lxf.servicepassengeruser.controller;

import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.VerificationCodeDTO;
import com.lxf.servicepassengeruser.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("user")
    public ResponseResult loginOrReg(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        return userService.loginOrRegister(passengerPhone);
    }
}
