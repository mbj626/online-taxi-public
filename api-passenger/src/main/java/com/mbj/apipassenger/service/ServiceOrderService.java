package com.mbj.apipassenger.service;

import com.mbj.apipassenger.remote.ServiceOrderClient;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 19:29
 * @Description:
 * @Version:
 */
@Service
public class ServiceOrderService {

    @Autowired
    ServiceOrderClient serviceOrderClient;

    public ResponseResult add(OrderRequest orderRequest){
        return serviceOrderClient.add(orderRequest);
    }

}
