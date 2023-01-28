package com.mbj.servicedriveruser.service;

import com.mbj.internalcommmon.dto.Car;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicedriveruser.mapper.CarMapper;
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
    CarMapper carMapper;

    public ResponseResult addCar(Car car){
        car.setGmtCreate(LocalDateTime.now());
        car.setGmtModified(LocalDateTime.now());
        carMapper.insert(car);
        return ResponseResult.success();
    }

}
