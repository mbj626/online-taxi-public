package com.mbj.apidriver.service;

import com.mbj.apidriver.remote.ServiceDriverUserClient;
import com.mbj.apidriver.remote.ServiceVerificationcodeClient;
import com.mbj.internalcommmon.constant.CommonStatusEnum;
import com.mbj.internalcommmon.constant.DriverConstant;
import com.mbj.internalcommmon.constant.IdentityConstant;
import com.mbj.internalcommmon.constant.TokenConstant;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.DriverUserExistsResponse;
import com.mbj.internalcommmon.response.NumberCodeResponse;
import com.mbj.internalcommmon.response.TokenResponse;
import com.mbj.internalcommmon.util.JwtUtils;
import com.mbj.internalcommmon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-28 21:42
 * @Description:
 * @Version:
 */
@Service
public class VerificationCodeService {

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    ServiceVerificationcodeClient serviceVerificationcodeClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        // 查询手机号是否存在
        ResponseResult<DriverUserExistsResponse> driverUserExistsResponseResponseResult = serviceDriverUserClient.checkDriverUser(driverPhone);
        DriverUserExistsResponse driverUserExistsResponse = driverUserExistsResponseResponseResult.getData();
        int isExists = driverUserExistsResponse.getIsExists();
        if (isExists == DriverConstant.DRIVER_NOT_EXISTS){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NO_EXIST.getCode(),CommonStatusEnum.DRIVER_NO_EXIST.getValue());
        }

        // 获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResult = serviceVerificationcodeClient.getNumberCode(6);
        NumberCodeResponse numberCodeResultData = numberCodeResult.getData();
        int numberCode = numberCodeResultData.getNumberCode();

        // 调用第三方发送验证码
        System.out.println("发送验证码");

        // 存入redis
        String key = RedisPrefixUtils.generatorKeyByPhone(driverPhone, IdentityConstant.DERIVER_IDENTITY);
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
        return ResponseResult.success("");
    }

    /**
     * 校验验证码
     * @param driverPhone 手机号
     * @param verificationCode 验证码
     * @return
     *
     */
    public ResponseResult checkCode(String driverPhone,String verificationCode){

        // 生成key 根据key获取value
        String key = RedisPrefixUtils.generatorKeyByPhone(driverPhone,IdentityConstant.DERIVER_IDENTITY);
        String codeRedis = stringRedisTemplate.opsForValue().get(key);

        // 校验验证码
        if (StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(codeRedis.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        // 颁发令牌token
        String accessToken = JwtUtils.generatorToken(driverPhone, IdentityConstant.DERIVER_IDENTITY, TokenConstant.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(driverPhone, IdentityConstant.DERIVER_IDENTITY,TokenConstant.REFRESH_TOKEN_TYPE);

        // 将token存到redis中
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(driverPhone, IdentityConstant.DERIVER_IDENTITY,TokenConstant.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);

        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(driverPhone, IdentityConstant.DERIVER_IDENTITY,TokenConstant.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);

        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
