package com.mbj.servicedriveruser.service;

import com.mbj.internalcommmon.dto.DriverUser;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-28 16:35
 * @Description:
 * @Version:
 */
@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverMapper;

    public ResponseResult addDriverUser(DriverUser driverUser) {
        driverUser.setGmtCreate(LocalDateTime.now());
        driverUser.setGmtModified(LocalDateTime.now());
        driverMapper.insert(driverUser);
        return ResponseResult.success();

    }

    public ResponseResult updateDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        driverMapper.updateById(driverUser);
        return ResponseResult.success();
    }
}
