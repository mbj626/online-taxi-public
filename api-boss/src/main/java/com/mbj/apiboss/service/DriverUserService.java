package com.mbj.apiboss.service;

import com.mbj.apiboss.remote.ServiceDriverUserClient;
import com.mbj.internalcommmon.dto.DriverUser;
import com.mbj.internalcommmon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-28 17:16
 * @Description:
 * @Version:
 */
@Service
public class DriverUserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addDriverUser(DriverUser driverUser){
        serviceDriverUserClient.addDriverUser(driverUser);
        return ResponseResult.success();
    }

    public ResponseResult updateDriverUser(DriverUser driverUser){
        serviceDriverUserClient.updateDriverUser(driverUser);
        return ResponseResult.success();
    }
}
