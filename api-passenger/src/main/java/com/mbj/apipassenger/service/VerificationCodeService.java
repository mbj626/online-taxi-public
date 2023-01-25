package com.mbj.apipassenger.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 14:25
 * @Description:
 * @Version:
 */
@Service
public class VerificationCodeService {

    public String generatorCode(String passengerCode){
        // 调用验证码服务，获取验证码
        System.out.println("获取验证码");
        String code = "111111";

        // 存入redis
        System.out.println("存入Redis");

        // 返回值
        JSONObject result = new JSONObject();
        result.put("code",1);
        result.put("messages","success");
        return result.toString();
    }

}
