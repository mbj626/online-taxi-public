package com.mbj.apipassenger.controller;

import com.mbj.apipassenger.service.UserService;
import com.mbj.internalcommmon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 23:18
 * @Description:
 * @Version:
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseResult getUser(HttpServletRequest request){

        // 从http请求中获取 accessToken
        String accessToken = request.getHeader("Authorization");
        return userService.getUserByAccessToken(accessToken);
    }

}
