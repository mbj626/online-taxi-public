package com.mbj.servicedriveruser.service;

import com.mbj.internalcommmon.constant.CommonStatusEnum;
import com.mbj.internalcommmon.constant.DriverConstant;
import com.mbj.internalcommmon.dto.DriverUser;
import com.mbj.internalcommmon.dto.DriverUserWorkStatus;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicedriveruser.mapper.DriverUserMapper;
import com.mbj.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult addDriverUser(DriverUser driverUser) {
        driverUser.setGmtCreate(LocalDateTime.now());
        driverUser.setGmtModified(LocalDateTime.now());
        driverMapper.insert(driverUser);
        // 初始化司机工作状态

        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverConstant.DRIVER_WORK_STATUS_STOP);
        driverUserWorkStatus.setGmtCreate(LocalDateTime.now());
        driverUserWorkStatus.setGmtModified(LocalDateTime.now());
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);
        return ResponseResult.success();

    }

    public ResponseResult updateDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        driverMapper.updateById(driverUser);
        return ResponseResult.success();
    }

    public ResponseResult<DriverUser>  getDriverUserByPhone(String driverPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        map.put("state", DriverConstant.DRIVER_STATE_VALID);
        List<DriverUser> driverUsers = driverMapper.selectByMap(map);
        if (driverUsers.isEmpty()){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NO_EXIST.getCode(),CommonStatusEnum.DRIVER_NO_EXIST.getValue());
        }
        DriverUser driverUser = driverUsers.get(0);
        return ResponseResult.success(driverUser);

    }
}
