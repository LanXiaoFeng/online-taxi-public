package com.lxf.apipassenger.service;

import com.lxf.internalcommon.dto.ResponseResult;

public interface UserService {

    ResponseResult getUserByAccessToken(String accessToken);
}
