package com.mbj.servicedriveruser.service;

import com.mbj.internalcommmon.dto.Car;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.TerminalResponse;
import com.mbj.internalcommmon.response.TrackResponse;
import com.mbj.servicedriveruser.mapper.CarMapper;
import com.mbj.servicedriveruser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult addCar(Car car){
        car.setGmtCreate(LocalDateTime.now());
        car.setGmtModified(LocalDateTime.now());
        carMapper.insert(car);

        // 获得此车辆对应的 tid
        ResponseResult<TerminalResponse> responseResult = serviceMapClient.addTerminal(car.getVehicleNo(),car.getId()+"");
        String tid = responseResult.getData().getTid();
        car.setTid(tid);

        // 获得此车辆轨迹的 trid
        ResponseResult<TrackResponse> trackResponseResponseResult = serviceMapClient.addTrack(tid);
        String trid = trackResponseResponseResult.getData().getTrid();
        String trname = trackResponseResponseResult.getData().getTrname();
        car.setTrid(trid);
        car.setTrname(trname);

        carMapper.updateById(car);
        return ResponseResult.success();
    }

    public ResponseResult<Car> getCarById(Long carId) {
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("id",carId);
        List<Car> cars = carMapper.selectByMap(queryMap);
        Car car = cars.get(0);
        return ResponseResult.success(car);
    }
}
