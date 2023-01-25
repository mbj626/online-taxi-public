package com.mbj.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mbj.internalcommmon.constant.TokenConstant;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.dto.TokenResult;
import com.mbj.internalcommmon.util.JwtUtils;
import com.mbj.internalcommmon.util.RedisPrefixUtils;
import jdk.nashorn.internal.parser.Token;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 21:14
 * @Description:
 * @Version:
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result = true;
        String resultString = "";

        String token = request.getHeader("Authorization");

        // 解析token
        TokenResult tokenResult = JwtUtils.checkToken(token);

        if (tokenResult == null){
            resultString = "access token invalid";
            result = false;
        }else {
            // 拼接key
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone,identity, TokenConstant.ACCESS_TOKEN_TYPE);
            // 从redis中取出token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(tokenRedis) || (!token.trim().equals(tokenRedis.trim()))){
                resultString = "access token invalid";
                result = false;
            }
        }

        if (!result){
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }

}
