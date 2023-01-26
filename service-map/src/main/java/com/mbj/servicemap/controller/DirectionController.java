package com.mbj.servicemap.controller;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.ForecastPriceDTO;
import com.mbj.servicemap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-26 20:49
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping("/direction")
public class DirectionController {

    @Autowired
    DirectionService directionService;

    @GetMapping("/driving")
    public ResponseResult driving(@RequestBody ForecastPriceDTO forecastPriceDTO){
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        return directionService.driving(depLongitude,depLatitude,destLongitude,destLatitude);
    }

}
