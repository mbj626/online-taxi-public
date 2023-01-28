package com.mbj.apidriver.controller;

import com.mbj.apidriver.service.TokenService;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 22:31
 * @Description:
 * @Version:
 */
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse){

        String refreshToken = tokenResponse.getRefreshToken();
        return tokenService.refreshToken(refreshToken);
    }
}
