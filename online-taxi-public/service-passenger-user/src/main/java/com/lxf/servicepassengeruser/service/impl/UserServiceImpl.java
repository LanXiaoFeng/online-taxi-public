package com.lxf.servicepassengeruser.service.impl;

import com.lxf.internalcommon.constant.CommonStatusEnum;
import com.lxf.internalcommon.dto.PassengerUser;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.servicepassengeruser.mapper.PassengerUserMapper;
import com.lxf.servicepassengeruser.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private PassengerUserMapper passengerUserMapper;

    @Override
    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("userService 被调用,手机号："+passengerPhone);
        // 根据手机号查询用户信息
        Map<String,Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        System.out.println(ObjectUtils.isEmpty(passengerUsers) ? "无记录":passengerUsers.get(0).getPassengerName());
        // 判断用户信息是否存在
        if (ObjectUtils.isEmpty(passengerUsers)){
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("张三");
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setState((byte) 0);
            passengerUser.setPassengerPhone(passengerPhone);
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);

            passengerUserMapper.insert(passengerUser);

        }
        // 如果不存在，插入用户信息
        return ResponseResult.success();
    }

    @Override
    public ResponseResult getUserByPhone(String passengerPhone) {
        // 根据手机号查询用户信息
        Map<String,Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if (ObjectUtils.isEmpty(passengerUsers)){
            return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXISTS.getCode(),CommonStatusEnum.USER_NOT_EXISTS.getValue());
        } else {
            PassengerUser passengerUser = passengerUsers.get(0);
            return ResponseResult.success(passengerUser);
        }
    }
}
