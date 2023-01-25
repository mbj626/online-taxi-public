package com.mbj.apipassenger.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mbj.apipassenger.remote.ServiceVerificationcodeClient;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String generatorCode(String passengerCode){
        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");

        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        // 存入redis
        System.out.println("存入Redis");

        return String.valueOf(numberCode);
    }

}
