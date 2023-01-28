package com.mbj.servicedriveruser.controller;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.VerificationCodeDTO;
import com.mbj.servicedriveruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 16:52
 * @Description:
 * @Version:
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println(passengerPhone);
        return userService.loginOrRegister(passengerPhone);
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String phone){
        return userService.getUserByPhone(phone);
    }

}
