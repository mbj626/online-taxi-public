package com.mbj.serviceprice.service;

import com.mbj.internalcommmon.constant.OrderConstants;
import com.mbj.internalcommmon.dto.OrderInfo;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.OrderRequest;
import com.mbj.serviceprice.mapper.OrderInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 20:00
 * @Description:
 * @Version:
 */
@Service
public class OrderInfoService {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    public ResponseResult add(@RequestBody OrderRequest orderRequest){
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest,orderInfo);



        orderInfo.setOrderStatus(OrderConstants.ORDER_START);

        orderInfo.setGmtCreate(LocalDateTime.now());
        orderInfo.setGmtModified(LocalDateTime.now());

        orderInfoMapper.insert(orderInfo);
        return ResponseResult.success();
    }

}
