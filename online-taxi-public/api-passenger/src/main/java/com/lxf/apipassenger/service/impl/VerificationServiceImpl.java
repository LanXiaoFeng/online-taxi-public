package com.lxf.apipassenger.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lxf.apipassenger.service.VerificationService;
import org.springframework.stereotype.Service;

@Service("verificationService")
public class VerificationServiceImpl implements VerificationService {

    @Override
    public String generatorCode(String passengerPhone) {
        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        String code = "11111";

        // 存入redis
        System.out.println("存入redis");

        // 返回值
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("message1","success");
        return jsonObject.toJSONString();
    }
}
