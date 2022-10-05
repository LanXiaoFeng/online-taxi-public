package com.lxf.apipassenger.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lxf.apipassenger.remote.ServiceVerificationcodeClient;
import com.lxf.apipassenger.service.VerificationService;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.response.NumberCodeResponse;
import com.lxf.internalcommon.response.TokenResponse;
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
        String key = generatorKeyByPhone(passengerPhone);
        stringRedisTemplate.opsForValue().set(key,numberCode +"", 1, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，荣联

        return ResponseResult.success("");
    }

    private String generatorKeyByPhone(String passengerPhone){
        return verificationCodePrefix + passengerPhone;
    }

    @Override
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        // 根据手机号， 去redis读取验证码
        // 生成key
        String key = generatorKeyByPhone(passengerPhone);
        // 根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的key：" + codeRedis);

        // 校验验证码
        System.out.println("校验验证码");

        // 判断原来是否有用户，并进行对应的处理
        System.out.println("判断原来是否有用户，并进行对应的处理");

        // 颁发令牌
        System.out.println("颁发令牌");

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token value");

        return ResponseResult.success(tokenResponse);
    }
}
