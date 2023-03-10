package com.mbj.apidriver.service;

import com.mbj.apidriver.remote.ServiceDriverUserClient;
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
public class UserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult updateDriverUser(DriverUser driverUser){
        serviceDriverUserClient.updateDriverUser(driverUser);
        return ResponseResult.success();
    }

}
