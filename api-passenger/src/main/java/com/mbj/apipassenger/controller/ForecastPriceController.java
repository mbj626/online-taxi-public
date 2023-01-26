package com.mbj.apipassenger.controller;

import com.mbj.apipassenger.remote.ServicePriceClient;
import com.mbj.apipassenger.service.ForecastPriceService;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.ForecastPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-26 20:12
 * @Description:
 * @Version:
 */
@RestController
@Slf4j
public class ForecastPriceController {

    @Autowired
    ForecastPriceService forecastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO){
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        return forecastPriceService.forecastPrice(depLongitude, depLatitude, destLongitude, destLatitude);
    }

}
