package com.lxf.apipassenger.service.impl;

import com.lxf.apipassenger.remote.ServicePassengerUserClient;
import com.lxf.apipassenger.service.UserService;
import com.lxf.internalcommon.dto.PassengerUser;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.dto.TokenResult;
import com.lxf.internalcommon.request.VerificationCodeDTO;
import com.lxf.internalcommon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private ServicePassengerUserClient servicePassengerUserClient;

    @Override
    public ResponseResult getUserByAccessToken(String accessToken) {
        log.info("accessToken："+ accessToken);
        // 解析accessToken，拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();
        log.info("手机号："+phone);

        // 根据手机号查询用户
        ResponseResult<PassengerUser> userByPhone = servicePassengerUserClient.getUserByPhone(phone);

        // 返回用户信息
        return ResponseResult.success(userByPhone.getData());
    }
}
