package com.mbj.apidriver.service;

import com.mbj.apidriver.remote.ServiceDriverUserClient;
import com.mbj.internalcommmon.dto.Car;
import com.mbj.internalcommmon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-28 17:54
 * @Description:
 * @Version:
 */
@Service
public class CarService {

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addCar(Car car){
        serviceDriverUserClient.addCar(car);
        return ResponseResult.success();
    }

}
