package com.lxf.apipassenger.service.impl;

import com.lxf.apipassenger.service.TokenService;
import com.lxf.internalcommon.constant.CommonStatusEnum;
import com.lxf.internalcommon.constant.IdentityConstants;
import com.lxf.internalcommon.constant.TokenConstants;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.dto.TokenResult;
import com.lxf.internalcommon.response.TokenResponse;
import com.lxf.internalcommon.util.JwtUtils;
import com.lxf.internalcommon.util.RedisPrefixUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Override
    public ResponseResult refreshToken(String refreshTokenSrc) {

        // 解析refreshtoken
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        if (tokenResult == null){
            return  ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String identity = tokenResult.getIdentity();
        String phone = tokenResult.getPhone();
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);

        // 读取redis中的refreshToken
        String refreshTokenNew = stringRedisTemplate.opsForValue().get(refreshTokenKey);
        // 校验refreshToken
        if (refreshTokenNew == null || !refreshTokenSrc.equals(refreshTokenNew)){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        // 生成双token
        String accessToken = JwtUtils.generatorToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);

        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

//        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,15,TimeUnit.SECONDS);
//        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,60, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31, TimeUnit.DAYS);

        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
