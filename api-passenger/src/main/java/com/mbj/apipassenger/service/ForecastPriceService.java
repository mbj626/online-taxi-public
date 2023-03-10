package com.mbj.apipassenger.service;

import com.mbj.apipassenger.remote.ServicePriceClient;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.ForecastPriceDTO;
import com.mbj.internalcommmon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ServicePriceClient servicePriceClient;

    /**
     * 根据 出发地和目的地经纬度 计算预估价格
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude,
                                        String cityCode,String vehicleType){
        log.info("出发地经度：" + depLongitude);
        log.info("出发地纬度：" + depLatitude);
        log.info("目的地经度：" + destLongitude);
        log.info("目的地经度：" + destLatitude);
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        forecastPriceDTO.setCityCode(cityCode);
        forecastPriceDTO.setVehicleType(vehicleType);
        ResponseResult<ForecastPriceResponse> forecast = servicePriceClient.forecast(forecastPriceDTO);
        return ResponseResult.success(forecast.getData());
    }

}
