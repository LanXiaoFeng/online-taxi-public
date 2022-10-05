package com.lxf.apipassenger.controller;

import com.lxf.apipassenger.request.VerificationCodeDTO;
import com.lxf.apipassenger.service.VerificationService;
import com.lxf.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class VerificationCodeController {

    @Resource
    private VerificationService verificationService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("接收到的手机号参数："+passengerPhone);
        return verificationService.generatorCode(passengerPhone);
    }
}
