package com.lxf.apipassenger.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lxf.apipassenger.remote.ServiceVerificationcodeClient;
import com.lxf.apipassenger.service.VerificationService;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.response.NumberCodeResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service("verificationService")
public class VerificationServiceImpl implements VerificationService {

    @Resource
    private ServiceVerificationcodeClient serviceVerificationcodeClient;

    private String verificationCodePrefix = "passenger-verification-code-";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseResult generatorCode(String passengerPhone) {
        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");

        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        System.out.println("remote number code: " + numberCode);

        // 存入redis
        System.out.println("存入redis");
        // key,value 过期时间
        String key = verificationCodePrefix + passengerPhone;
        stringRedisTemplate.opsForValue().set(key,numberCode +"", 1, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，荣联

        return ResponseResult.success("");
    }
}
