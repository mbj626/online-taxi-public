package com.mbj.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mbj.internalcommmon.constant.CommonStatusEnum;
import com.mbj.internalcommmon.constant.OrderConstants;
import com.mbj.internalcommmon.dto.OrderInfo;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.OrderRequest;
import com.mbj.internalcommmon.util.RedisPrefixUtils;
import com.mbj.serviceorder.mapper.OrderInfoMapper;
import com.mbj.serviceorder.remote.ServicePriceClient;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 20:00
 * @Description:
 * @Version:
 */
@Service
@Slf4j
public class OrderInfoService {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    ServicePriceClient servicePriceClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public ResponseResult add(@RequestBody OrderRequest orderRequest){
        // 判断计价规则版本是否为最新
        ResponseResult<Boolean> aNew = servicePriceClient.isNew(orderRequest.getFareType(), orderRequest.getFareVersion());
        Boolean data = aNew.getData();
        if (!data){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(),CommonStatusEnum.PRICE_RULE_CHANGED.getValue());
        }

        // 判断下单的设备是否是黑名单设备
        String deviceCode = orderRequest.getDeviceCode();
        // 生成key
        String deviceCodeKey = RedisPrefixUtils.blackDeviceCodePrefix + deviceCode;
        if (isBlackDevice(deviceCodeKey))
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getValue());

        // 判断有没有正在进行的订单
        if (isOrderGoingOn(orderRequest.getPassengerId()) > 0){
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(),CommonStatusEnum.ORDER_GOING_ON.getValue());
        }

        // 创建订单
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest,orderInfo);

        orderInfo.setOrderStatus(OrderConstants.ORDER_START);

        orderInfo.setGmtCreate(LocalDateTime.now());
        orderInfo.setGmtModified(LocalDateTime.now());

        orderInfoMapper.insert(orderInfo);
        return ResponseResult.success();
    }

    /**
     * 判断是否黑名单设备
     * @param deviceCodeKey
     * @return
     */
    private boolean isBlackDevice(String deviceCodeKey) {
        Boolean aBoolean = stringRedisTemplate.hasKey(deviceCodeKey);
        if (aBoolean){
            String s = stringRedisTemplate.opsForValue().get(deviceCodeKey);
            assert s != null;
            int i = Integer.parseInt(s);
            if (i >= 2){
                // 当前设备超过下单次数
                return true;
            }else {
                stringRedisTemplate.opsForValue().increment(deviceCodeKey);
            }
        }else {
            stringRedisTemplate.opsForValue().setIfAbsent(deviceCodeKey,"1",1L, TimeUnit.HOURS);
        }
        return false;
    }

    /**
     * 是否有 业务中的订单
     * @param passengerId
     * @return
     */
    private int isOrderGoingOn(Long passengerId){
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id",passengerId);
        queryWrapper.and(wrapper->wrapper.eq("order_status",OrderConstants.ORDER_START)
                .or().eq("order_status",OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status",OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status",OrderConstants.PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.PASSENGER_GETOFF)
                .or().eq("order_status",OrderConstants.TO_START_PAY));
        return orderInfoMapper.selectCount(queryWrapper);
    }
}
