package com.mbj.apipassenger.remote;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.ForecastPriceDTO;
import com.mbj.internalcommmon.response.ForecastPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-26 22:47
 * @Description:
 * @Version:
 */
@FeignClient("service-price")
public interface ServicePriceClient {

    @RequestMapping(method = RequestMethod.POST,value = "/forecast-price")
    public ResponseResult<ForecastPriceResponse> forecast(@RequestBody ForecastPriceDTO forecastPriceDTO);

}
