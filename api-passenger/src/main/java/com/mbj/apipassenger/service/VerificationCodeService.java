package com.mbj.apipassenger.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mbj.apipassenger.remote.ServiceVerificationcodeClient;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
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

    public ResponseResult generatorCode(String passengerPhone){
        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");

        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        // key,value,过期时间
        String key = verificationCodePrefix + passengerPhone;
        // 存入redis
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);

        // 通过短信服务商，将验证码发送到手机上

        return ResponseResult.success();
    }

}
