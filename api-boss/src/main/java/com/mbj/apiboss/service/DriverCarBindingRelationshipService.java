package com.mbj.apiboss.service;

import com.mbj.apiboss.remote.ServiceDriverUserClient;
import com.mbj.internalcommmon.dto.DriverCarBindingRelationship;
import com.mbj.internalcommmon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-28 20:08
 * @Description:
 * @Version:
 */
@Service
public class DriverCarBindingRelationshipService {

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship){
        return serviceDriverUserClient.bind(driverCarBindingRelationship);
    }

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        return serviceDriverUserClient.unbind(driverCarBindingRelationship);
    }
}
