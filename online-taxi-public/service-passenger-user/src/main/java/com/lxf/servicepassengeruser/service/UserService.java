package com.lxf.servicepassengeruser.service;

import com.lxf.internalcommon.dto.ResponseResult;

public interface UserService {

    ResponseResult loginOrRegister(String passengerPhone);
}
