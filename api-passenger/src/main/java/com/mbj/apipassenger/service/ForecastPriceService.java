package com.mbj.apipassenger.service;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-26 20:16
 * @Description:
 * @Version:
 */
@Service
@Slf4j
public class ForecastPriceService {

    /**
     * 根据 出发地和目的地经纬度 计算预估价格
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude){
        log.info("出发地经度：" + depLongitude);
        log.info("出发地纬度：" + depLatitude);
        log.info("目的地经度：" + destLongitude);
        log.info("目的地经度：" + destLatitude);

        log.info("");

        log.info("");

        log.info("");

        log.info("");

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.34);
        return ResponseResult.success(forecastPriceResponse);
    }

}
