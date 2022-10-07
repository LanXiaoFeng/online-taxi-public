package com.lxf.apipassenger.service;

import com.lxf.internalcommon.dto.ResponseResult;

public interface TokenService {
    ResponseResult refreshToken(String refreshTokenSrc);
}
