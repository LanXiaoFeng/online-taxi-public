package com.lxf.apipassenger.service;

import com.lxf.internalcommon.dto.ResponseResult;

public interface VerificationService {
    public ResponseResult generatorCode(String passengerPhone);
}
