package com.mbj.servicedriveruser.service;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-30 16:06
 * @Description:
 * @Version:
 */
@Service
public class CityDriverUserService {

    @Autowired
    DriverUserMapper driverUserMapper;

    public ResponseResult<Boolean> isAvailableDriver(String cityCode){
        int i = driverUserMapper.selectDriverUserCountByCityCode(cityCode);
        if (i > 0){
            return ResponseResult.success(true);
        }else
            return ResponseResult.success(false);
    }

}
