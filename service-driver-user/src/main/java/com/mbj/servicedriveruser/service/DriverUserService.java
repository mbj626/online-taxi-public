package com.mbj.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mbj.internalcommmon.constant.CommonStatusEnum;
import com.mbj.internalcommmon.constant.DriverConstant;
import com.mbj.internalcommmon.dto.*;
import com.mbj.internalcommmon.response.OrderDriverResponse;
import com.mbj.servicedriveruser.mapper.CarMapper;
import com.mbj.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.mbj.servicedriveruser.mapper.DriverUserMapper;
import com.mbj.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
    private DriverUserMapper driverUserMapper;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult addDriverUser(DriverUser driverUser) {
        driverUser.setGmtCreate(LocalDateTime.now());
        driverUser.setGmtModified(LocalDateTime.now());
        driverUserMapper.insert(driverUser);
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
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success();
    }

    public ResponseResult<DriverUser>  getDriverUserByPhone(String driverPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        map.put("state", DriverConstant.DRIVER_STATE_VALID);
        List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);
        if (driverUsers.isEmpty()){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NO_EXIST.getCode(),CommonStatusEnum.DRIVER_NO_EXIST.getValue());
        }
        DriverUser driverUser = driverUsers.get(0);
        return ResponseResult.success(driverUser);
    }

    @Autowired
    DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    public ResponseResult<OrderDriverResponse> getAvailableDriver(Long carId){
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id",carId);
        queryWrapper.eq("bind_state",DriverConstant.DRIVER_CAR_BIND);

        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(queryWrapper);
        Long driverId = driverCarBindingRelationship.getDriverId();

        QueryWrapper<DriverUserWorkStatus> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("driver_id",driverId);
        queryWrapper1.eq("work_status",DriverConstant.DRIVER_WORK_STATUS_START);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusMapper.selectOne(queryWrapper1);
        if (null == driverUserWorkStatus){
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode(),CommonStatusEnum.AVAILABLE_DRIVER_EMPTY
                    .getValue());
        }else {
            QueryWrapper<DriverUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id",driverId);
            DriverUser driverUser = driverUserMapper.selectOne(queryWrapper2);

            QueryWrapper<Car> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("id",carId);
            Car car = carMapper.selectOne(queryWrapper3);

            OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
            orderDriverResponse.setCarId(carId);
            orderDriverResponse.setDriverId(driverId);
            orderDriverResponse.setDriverPhone(driverUser.getDriverPhone());
            orderDriverResponse.setLicenseId(driverUser.getLicenseId());
            orderDriverResponse.setVehicleNo(car.getVehicleNo());

            return ResponseResult.success(orderDriverResponse);
        }
    }
}
