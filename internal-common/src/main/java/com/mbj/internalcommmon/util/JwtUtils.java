package com.mbj.internalcommmon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mbj.internalcommmon.dto.TokenResult;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 20:26
 * @Description:
 * @Version:
 */
public class JwtUtils {

    // 盐
    private static final String SIGN = "CPASFA#@@s$%";

    private static final String JWT_KEY_PHONE = "phone";

    // 假定乘客是1 司机是2
    private static final String JWT_kEY_IDENTITY = "identity";

    // token类型
    private static final String JWT_TOKEN_TYPE = "tokenType";

    private static final String JWT_TOKEN_TIME = "tokenTime";

    // 生成token
    public static String generatorToken(String passengerPhone,String identity,String tokenType){
        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE,passengerPhone);
        map.put(JWT_kEY_IDENTITY,identity);
        map.put(JWT_TOKEN_TYPE,tokenType);
        // 防止每次生成的token一样
        map.put(JWT_TOKEN_TIME,Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();
        // 整合map
        map.forEach((k,v) -> {
            builder.withClaim(k,v);
        });
        // 整合过期时间
        //builder.withExpiresAt(date);

        // 生成token
        return builder.sign(Algorithm.HMAC256(SIGN));
    }

    // 解析token
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_kEY_IDENTITY).asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    /**
     * 校验token，主要判断token是否异常
     * @param token
     * @return
     */
    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        }catch (Exception e) {
        }
        return tokenResult;
    }

    public static void main(String[] args) {
        String s = generatorToken("19935432804","1","access");
        System.out.println(s);
        TokenResult tokenResult = parseToken(s);
        System.out.println(tokenResult.getPhone());
        System.out.println(tokenResult.getIdentity());
    }
}
