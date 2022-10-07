package com.lxf.apipassenger.service.impl;

import com.lxf.apipassenger.service.UserService;
import com.lxf.internalcommon.dto.PassengerUser;
import com.lxf.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public ResponseResult getUserByAccessToken(String accessToken) {
        log.info("accessToken："+ accessToken);
        // 解析accessToken，拿到手机号

        // 根据手机号查询用户
        // 返回用户信息
        PassengerUser passengerUser = new PassengerUser();
        passengerUser.setPassengerName("张三");
        passengerUser.setProfilePhoto("头像");
        return ResponseResult.success(passengerUser);
    }
}
