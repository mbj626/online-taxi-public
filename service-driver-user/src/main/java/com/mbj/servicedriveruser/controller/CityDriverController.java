package com.mbj.servicedriveruser.controller;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicedriveruser.service.CityDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-30 16:08
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping("/city-driver")
public class CityDriverController {

    @Autowired
    CityDriverUserService cityDriverUserService;

    /**
     * 根据城市编码查询当前城市是否有可用司机
     * @param cityCode
     * @return
     */
    @GetMapping("/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(@RequestParam("cityCode") String cityCode){
        return cityDriverUserService.isAvailableDriver(cityCode);
    }

}
