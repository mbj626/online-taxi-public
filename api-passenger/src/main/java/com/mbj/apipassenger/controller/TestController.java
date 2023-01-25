package com.mbj.apipassenger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 14:04
 * @Description:
 * @Version:
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test api passenger";
    }
}
