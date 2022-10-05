package com.lxf.apipassenger.service;

import com.lxf.internalcommon.dto.ResponseResult;

public interface VerificationService {
    ResponseResult generatorCode(String passengerPhone);

    ResponseResult checkCode(String passengerPhone, String verificationCode);
}
