package com.mbj.apidriver.service;

import com.mbj.apidriver.remote.ServiceDriverUserClient;
import com.mbj.apidriver.remote.ServiceMapClient;
import com.mbj.internalcommmon.dto.Car;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.ApiDriverPointRequest;
import com.mbj.internalcommmon.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 15:23
 * @Description:
 * @Version:
 */
@Service
public class PointService {

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    ServiceMapClient serviceMapClient;

    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest){

        // 获取carId
        Long carId = apiDriverPointRequest.getCarId();

        // 通过carId 获取 tid,trid  调用service-driver-user的接口
        ResponseResult<Car> upload = serviceDriverUserClient.upload(carId);
        String tid = upload.getData().getTid();
        String trid = upload.getData().getTrid();

        // 调用地图去上传
        PointRequest pointRequest = new PointRequest();
        pointRequest.setTid(tid);
        pointRequest.setTrid(trid);
        pointRequest.setPoints(apiDriverPointRequest.getPoints());

        return serviceMapClient.upload(pointRequest);
    }

}
