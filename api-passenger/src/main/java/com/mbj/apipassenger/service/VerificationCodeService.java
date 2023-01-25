package com.mbj.apipassenger.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mbj.apipassenger.remote.ServiceVerificationcodeClient;
import com.mbj.internalcommmon.constant.CommonStatusEnum;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.NumberCodeResponse;
import com.mbj.internalcommmon.response.TokenResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 14:25
 * @Description:
 * @Version:
 */
@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationcodeClient serviceVerificationcodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 乘客验证码前缀
    private String verificationCodePrefix = "passenger-verification-code-";

    /**
     * 生成验证码
     * @param passengerPhone
     * @return
     */
    public ResponseResult generatorCode(String passengerPhone){
        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");

        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        // key,value,过期时间
        String key = generatorKeyByPhone(passengerPhone);
        // 存入redis
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);

        // 通过短信服务商，将验证码发送到手机上

        return ResponseResult.success();
    }

    private String generatorKeyByPhone(String passengerPhone){
        return verificationCodePrefix + passengerPhone;
    }

    /**
     * 校验验证码
     * @param passengerPhone 手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String passengerPhone,String verificationCode){

        // 根据手机号去Redis读取验证码

        // 生成key 根据key获取value
        String key = generatorKeyByPhone(passengerPhone);
        String codeRedis = stringRedisTemplate.opsForValue().get(key);

        // 校验验证码
        if (StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(codeRedis.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        // 判断原来是否有用户，并进行对应的处理

        // 颁发令牌token

        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token value");
        return ResponseResult.success(tokenResponse);
    }
}
