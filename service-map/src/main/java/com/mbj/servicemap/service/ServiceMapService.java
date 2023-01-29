package com.mbj.servicemap.service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 13:50
 * @Description:
 * @Version:
 */

import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.servicemap.remote.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceMapService {

    @Autowired
    ServiceClient serviceClient;

    public ResponseResult add(String name){
        return serviceClient.add(name);
    }

}
