package com.lxf.apipassenger.service.impl;

import com.lxf.apipassenger.remote.ServicePassengerUserClient;
import com.lxf.apipassenger.remote.ServiceVerificationcodeClient;
import com.lxf.apipassenger.service.VerificationService;
import com.lxf.internalcommon.constant.CommonStatusEnum;
import com.lxf.internalcommon.constant.IdentityConstant;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.request.VerificationCodeDTO;
import com.lxf.internalcommon.response.NumberCodeResponse;
import com.lxf.internalcommon.response.TokenResponse;
import com.lxf.internalcommon.util.JwtUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.lxf.internalcommon.util.RedisPrefixUtils.generatorKeyByPhone;
import static com.lxf.internalcommon.util.RedisPrefixUtils.generatorTokenKey;

@Service("verificationService")
public class VerificationServiceImpl implements VerificationService {

    @Resource
    private ServiceVerificationcodeClient serviceVerificationcodeClient;
    @Resource
    private ServicePassengerUserClient servicePassengerUserClient;



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
        stringRedisTemplate.opsForValue().set(key,numberCode +"", 2, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，荣联

        return ResponseResult.success("");
    }


    @Override
    public ResponseResult checkCode(VerificationCodeDTO verificationCodeDTO) {
        // 根据手机号， 去redis读取验证码
        // 生成key
        String key = generatorKeyByPhone(verificationCodeDTO.getPassengerPhone());
        // 根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的key：" + codeRedis);

        // 校验验证码
        System.out.println("校验验证码");
        if(StringUtils.isEmpty(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCodeDTO.getVerificationCode().trim().equals(codeRedis.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        // 判断原来是否有用户，并进行对应的处理
        System.out.println("判断原来是否有用户，并进行对应的处理");
        servicePassengerUserClient.loginOrReg(verificationCodeDTO);
        // 颁发令牌 不应该用魔法值，用常量
        String token = JwtUtils.generatorToken(verificationCodeDTO.getPassengerPhone(), IdentityConstant.PASSENGER_IDENTITY);
        // 将token存到redis当中
        String tokenKey = generatorTokenKey(verificationCodeDTO.getPassengerPhone(), IdentityConstant.PASSENGER_IDENTITY);
        stringRedisTemplate.opsForValue().set(tokenKey,token,30,TimeUnit.DAYS);


        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
        return ResponseResult.success(tokenResponse);
    }
}
