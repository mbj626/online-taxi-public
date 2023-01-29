package com.mbj.serviceprice.remote;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.ForecastPriceDTO;
import com.mbj.internalcommmon.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-26 21:49
 * @Description:
 * @Version:
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.GET,value = "/direction/driving")
    public ResponseResult<DirectionResponse> direction(@RequestBody ForecastPriceDTO forecastPriceDTO);

}
