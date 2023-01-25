package com.mbj.serviceverificationcode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 14:52
 * @Description:
 * @Version:
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "service-verificationcode";
    }

}
