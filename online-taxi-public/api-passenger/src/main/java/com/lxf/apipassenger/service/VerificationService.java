package com.lxf.apipassenger.service;

import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.VerificationCodeDTO;

public interface VerificationService {
    ResponseResult generatorCode(String passengerPhone);

    ResponseResult checkCode(VerificationCodeDTO verificationCodeDTO);
}
