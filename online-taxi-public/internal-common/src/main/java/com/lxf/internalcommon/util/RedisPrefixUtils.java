package com.lxf.internalcommon.util;

public class RedisPrefixUtils {

    public static String verificationCodePrefix = "passenger-verification-code-";

    public static String tokenPrefix = "token-";

    public static String generatorKeyByPhone(String phone){
        return verificationCodePrefix + phone;
    }

    public static String generatorTokenKey(String phone, String identity, String tokenType){
        return tokenPrefix+phone+"-"+identity+"-"+tokenType;
    }
}
