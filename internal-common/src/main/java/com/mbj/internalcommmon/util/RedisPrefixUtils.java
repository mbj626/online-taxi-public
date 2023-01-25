package com.mbj.internalcommmon.util;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 21:42
 * @Description:
 * @Version:
 */
public class RedisPrefixUtils {

    // 乘客验证码前缀
    public static String verificationCodePrefix = "passenger-verification-code-";

    // token前缀
    public static String tokenPrefix = "token-";

    /**
     * 根据手机号生成key
     * @param passengerPhone
     * @return
     */
    public static String generatorKeyByPhone(String passengerPhone){
        return verificationCodePrefix + passengerPhone;
    }

    /**
     * 根据手机号和身份标识生成key
     * @param phone
     * @param identity
     * @return
     */
    public static String generatorTokenKey(String phone,String identity,String tokenType){
        return tokenPrefix + phone + "-" + identity  + "-" + tokenType;
    }
}
