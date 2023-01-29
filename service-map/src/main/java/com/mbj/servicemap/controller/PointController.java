package com.mbj.servicemap.controller;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.PointRequest;
import com.mbj.servicemap.service.PointService;
import com.mbj.servicemap.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 14:06
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    PointService pointService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest){
        return pointService.upload(pointRequest);
    }


}
