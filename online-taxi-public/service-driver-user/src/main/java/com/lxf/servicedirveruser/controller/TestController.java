package com.lxf.servicedirveruser.controller;

import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.servicedirveruser.service.DriverUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private DriverUserService driverUserService;

    @RequestMapping("/test")
    public ResponseResult test(){
        return driverUserService.testGetDriverUser();
    }
}
