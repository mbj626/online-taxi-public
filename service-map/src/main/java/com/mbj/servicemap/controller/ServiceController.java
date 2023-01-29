package com.mbj.servicemap.controller;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicemap.service.ServiceMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 13:49
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    ServiceMapService serviceMapService;

    /**
     * 创建服务
     * @param name
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(String name){
        return serviceMapService.add(name);
    }

}
