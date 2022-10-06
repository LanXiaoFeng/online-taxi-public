package com.lxf.apipassenger.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.lxf.internalcommon.dto.ResponseResult;
import com.lxf.internalcommon.dto.TokenResult;
import com.lxf.internalcommon.util.JwtUtils;
import com.lxf.internalcommon.util.RedisPrefixUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean resutl = true;
        String reslutString = "";

        String token = request.getHeader("Authorization");
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        }catch (SignatureVerificationException e){
            reslutString = "token sign error";
            resutl = false;
        }catch (TokenExpiredException e){
            reslutString = "token time out";
            resutl = false;
        }catch (Exception e) {
            reslutString = "token invalid";
            resutl = false;
        }

        if (tokenResult == null){
            reslutString = "token invalid";
            resutl = false;
        }else {
            // 拼接key
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity);
            // 从redis中获取token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isEmpty(tokenRedis)){
                reslutString = "token invalid";
                resutl = false;
            }else {
                if (!token.trim().equals(tokenRedis.trim())){
                    reslutString = "token invalid";
                    resutl = false;
                }
            }
        }

        if (!resutl){
            PrintWriter writer = response.getWriter();
            writer.print(JSONObject.toJSON(ResponseResult.fail(reslutString)).toString());
        }

        return resutl;
    }
}
