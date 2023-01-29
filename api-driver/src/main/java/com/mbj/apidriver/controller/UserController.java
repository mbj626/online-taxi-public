package com.mbj.apidriver.controller;

import com.mbj.apidriver.service.UserService;
import com.mbj.internalcommmon.dto.DriverUser;
import com.mbj.internalcommmon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-28 17:13
 * @Description:
 * @Version:
 */
@RestController
public class UserController {

    @Autowired
    private UserService driverUserService;

    /**
     * 修改司机
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser){
        driverUserService.updateDriverUser(driverUser);
        return ResponseResult.success();
    }

}
