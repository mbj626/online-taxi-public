package com.mbj.servicemap.service;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.DirectionResponse;
import com.mbj.internalcommmon.response.ForecastPriceResponse;
import com.mbj.servicemap.remote.MapDirectionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-26 20:37
 * @Description:
 * @Version:
 */
@Service
@Slf4j
public class DirectionService {

    @Autowired
    MapDirectionClient mapDirectionClient;

    /**
     * 根据 出发地和目的地经纬度 计算距离（米）和时长（分钟）
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude){
        // 调用第三方接口
        DirectionResponse direction = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);
        return ResponseResult.success(direction);
    }

}
