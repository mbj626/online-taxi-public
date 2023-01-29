package com.mbj.apipassenger.service;

import com.mbj.apipassenger.remote.ServicePassengerUserClient;
import com.mbj.internalcommmon.dto.PassengerUser;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.dto.TokenResult;
import com.mbj.internalcommmon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 23:19
 * @Description:
 * @Version:
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserByAccessToken(String accessToken){
        log.info("accessToken:" + accessToken);
        // 解析accessToken，拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("phone:" + phone);
        // 根据手机号查询用户信息
        ResponseResult<PassengerUser> userByPhone = servicePassengerUserClient.getUserByPhone(phone);

        return ResponseResult.success(userByPhone.getData());

    }
}
