package com.mbj.serviceorder.remote;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.OrderDriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-30 16:14
 * @Description:
 * @Version:
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @GetMapping("/city-driver/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(@RequestParam("cityCode") String cityCode);

    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId);

}
