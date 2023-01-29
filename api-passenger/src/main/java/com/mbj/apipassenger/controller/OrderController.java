package com.mbj.apipassenger.controller;

import com.mbj.apipassenger.service.ServiceOrderService;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 19:25
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    ServiceOrderService orderService;

    /**
     * 创建订单、下单
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest){
        return orderService.add(orderRequest);
    }

}
