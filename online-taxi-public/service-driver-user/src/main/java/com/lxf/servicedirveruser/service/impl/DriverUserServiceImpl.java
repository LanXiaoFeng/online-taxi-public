package com.lxf.servicedirveruser.service.impl;

import com.lxf.internalcommon.dto.DriverUser;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.servicedirveruser.mapper.DriverUserMapper;
import com.lxf.servicedirveruser.service.DriverUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DriverUserServiceImpl implements DriverUserService {

    @Resource
    private DriverUserMapper driverUserMapper;

    @Override
    public ResponseResult testGetDriverUser() {
        DriverUser driverUser = driverUserMapper.selectById("1542494575889854466");
        return ResponseResult.success(driverUser);
    }
}
