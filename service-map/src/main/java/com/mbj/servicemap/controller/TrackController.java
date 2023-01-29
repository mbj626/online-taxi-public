package com.mbj.servicemap.controller;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicemap.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 14:06
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping("/track")
public class TrackController {

    @Autowired
    TrackService trackService;

    @PostMapping("/add")
    public ResponseResult add(String tid){
        return trackService.add(tid);
    }


}
