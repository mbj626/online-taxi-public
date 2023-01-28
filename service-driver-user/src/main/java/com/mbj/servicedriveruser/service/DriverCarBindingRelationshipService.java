package com.mbj.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mbj.internalcommmon.constant.CommonStatusEnum;
import com.mbj.internalcommmon.constant.DriverConstant;
import com.mbj.internalcommmon.dto.DriverCarBindingRelationship;
import com.mbj.internalcommmon.dto.DriverUser;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicedriveruser.mapper.CarMapper;
import com.mbj.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.mbj.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-28 20:08
 * @Description:
 * @Version:
 */
@Service
public class DriverCarBindingRelationshipService {

    @Autowired
    DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship){
        // 判断是否绑定过
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverConstant.DRIVER_CAR_BIND);
        Integer integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (integer > 0 ){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXIST.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_EXIST.getValue());
        }

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_state",DriverConstant.DRIVER_CAR_BIND);
        integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (integer > 0 ){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXIST.getCode(),CommonStatusEnum.DRIVER_BIND_EXIST.getValue());
        }


        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverConstant.DRIVER_CAR_BIND);
        integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (integer > 0 ){
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXIST.getCode(),CommonStatusEnum.CAR_BIND_EXIST.getValue());
        }

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverConstant.DRIVER_CAR_UNBIND);
        integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (integer > 0 ){
            DriverCarBindingRelationship result = driverCarBindingRelationshipMapper.selectOne(queryWrapper);
            result.setBindState(DriverConstant.DRIVER_CAR_BIND);
            result.setBindingTime(LocalDateTime.now());
            driverCarBindingRelationshipMapper.updateById(result);
            return ResponseResult.success("绑定成功");
        }

        driverCarBindingRelationship.setBindingTime(LocalDateTime.now());
        driverCarBindingRelationship.setBindState(DriverConstant.DRIVER_CAR_BIND);
        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("绑定成功");
    }

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        // 判断是否绑定过
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverConstant.DRIVER_CAR_BIND);
        DriverCarBindingRelationship result = driverCarBindingRelationshipMapper.selectOne(queryWrapper);
        if (result == null){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXIST.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXIST.getValue());
        }
        result.setBindState(DriverConstant.DRIVER_CAR_UNBIND);
        result.setUnBindingTime(LocalDateTime.now());
        driverCarBindingRelationshipMapper.updateById(result);
        return ResponseResult.success("解绑成功");
    }
}
