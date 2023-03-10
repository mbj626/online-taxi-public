package com.mbj.apidriver.remote;

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.PointRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 14:18
 * @Description:
 * @Version:
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST,value = "/point/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest);

}
