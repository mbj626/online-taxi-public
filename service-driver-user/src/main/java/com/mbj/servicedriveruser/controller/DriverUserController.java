package com.mbj.servicedriveruser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbj.internalcommmon.dto.Car;
import com.mbj.internalcommmon.dto.DriverUser;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicedriveruser.service.DriverUserService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * (DriverUser)表控制层
 *
 * @author makejava
 * @since 2023-01-28 16:33:37
 */
@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser){
        driverUserService.addDriverUser(driverUser);
        return ResponseResult.success();
    }

    @PutMapping("/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser){
        driverUserService.updateDriverUser(driverUser);
        return ResponseResult.success();
    }
    public static void main(String[] args) throws JsonProcessingException {
        Car car = new Car();
        ObjectMapper mapper = new ObjectMapper();
                 // Convert object to JSON string
                String jsonStr = "";
                 try {
                          jsonStr =  mapper.writeValueAsString(car);
                    } catch (IOException e) {
                         throw e;
                     }
        System.out.println(JSONObject.fromObject(car).toString());
    }
}

