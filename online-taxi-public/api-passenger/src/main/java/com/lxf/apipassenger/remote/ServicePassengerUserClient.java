package com.lxf.apipassenger.remote;

import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @RequestMapping(method = RequestMethod.POST,value = "/user")
    ResponseResult loginOrReg(@RequestBody VerificationCodeDTO verificationCodeDTO);
}
