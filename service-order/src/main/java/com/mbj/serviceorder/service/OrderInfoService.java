package com.mbj.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mbj.internalcommmon.constant.CommonStatusEnum;
import com.mbj.internalcommmon.constant.OrderConstants;
import com.mbj.internalcommmon.dto.Car;
import com.mbj.internalcommmon.dto.OrderInfo;
import com.mbj.internalcommmon.dto.PriceRule;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.request.OrderRequest;
import com.mbj.internalcommmon.request.PriceRuleIsNewRequest;
import com.mbj.internalcommmon.response.OrderDriverResponse;
import com.mbj.internalcommmon.response.TerminalResponse;
import com.mbj.internalcommmon.util.RedisPrefixUtils;
import com.mbj.serviceorder.mapper.OrderInfoMapper;
import com.mbj.serviceorder.remote.ServiceDriverUserClient;
import com.mbj.serviceorder.remote.ServiceMapClient;
import com.mbj.serviceorder.remote.ServicePriceClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public ResponseResult add(@RequestBody OrderRequest orderRequest){

        // 判断下单的城市和车型是否有计价规则
        if (!isPriceRuleExists(orderRequest)){
            return ResponseResult.fail(CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getCode(),CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getValue());
        }

        // 判断计价规则版本是否为最新
        PriceRuleIsNewRequest priceRuleIsNewRequest = new PriceRuleIsNewRequest();
        priceRuleIsNewRequest.setFareType(orderRequest.getFareType());
        priceRuleIsNewRequest.setFareVersion(orderRequest.getFareVersion());
        ResponseResult<Boolean> aNew = servicePriceClient.isNew(priceRuleIsNewRequest);
        Boolean data = aNew.getData();
        if (!data){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(),CommonStatusEnum.PRICE_RULE_CHANGED.getValue());
        }

        // 判断城市是否有可用司机
        ResponseResult<Boolean> availableDriver = serviceDriverUserClient.isAvailableDriver(orderRequest.getAddress());
        if(!availableDriver.getData()){
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_EMPTY.getCode(),CommonStatusEnum.CITY_DRIVER_EMPTY.getValue());
        }

        // 判断乘客有没有正在进行的订单
        if (isPassengerOrderGoingOn(orderRequest.getPassengerId()) > 0){
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(),CommonStatusEnum.ORDER_GOING_ON.getValue());
        }

        // 判断下单的设备是否是黑名单设备
        if (isBlackDevice(orderRequest))
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getValue());

        // 创建订单
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest,orderInfo);

        orderInfo.setOrderStatus(OrderConstants.ORDER_START);

        orderInfo.setGmtCreate(LocalDateTime.now());
        orderInfo.setGmtModified(LocalDateTime.now());

        orderInfoMapper.insert(orderInfo);
        // 派单
        dispatchRealTimeOrder(orderInfo);
        return ResponseResult.success();
    }

    @Autowired
    ServiceMapClient serviceMapClient;

    /**
     * 实时订单派单逻辑
     * @param orderInfo
     */
    public void dispatchRealTimeOrder(OrderInfo orderInfo){

        // 2km
        String depLatitude = orderInfo.getDepLatitude();
        String depLongitude = orderInfo.getDepLongitude();
        int radius = 2000;
        String center = depLatitude + "," + depLongitude;

        List<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);
        // 搜索结果
        ResponseResult<List<TerminalResponse>> listResponseResult = null;
        radius:
        for (Integer radiusInteger : radiusList) {
            listResponseResult = serviceMapClient.terminalAroundSearch(center, radiusInteger);
            log.info("在半径为: " + radiusInteger + "的范围内寻找车辆");
            // 解析终端
            List<TerminalResponse> data = listResponseResult.getData();
            for (int i = 0; i < data.size(); i++) {
                TerminalResponse terminalResponse = data.get(i);
                Long carId = terminalResponse.getCarId();
                String longitude = terminalResponse.getLongitude();
                String latitude = terminalResponse.getLatitude();
                // 查询是否有对应的可派单司机
                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                if (availableDriver.getCode() == CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode()){
                    continue;
                }else {
                    log.info("找到了正在出车的司机，车辆ID为" + carId);
                    OrderDriverResponse orderDriverResponse = availableDriver.getData();
                    Long driverId = orderDriverResponse.getDriverId();
                    String driverPhone = orderDriverResponse.getDriverPhone();
                    String licenseId = orderDriverResponse.getLicenseId();
                    String vehicleNo = orderDriverResponse.getVehicleNo();
                    // 判断司机有没有正在进行的订单
                    if (isDriverOrderGoingOn(driverId) > 0){
                        continue;
                    }
                    // 订单直接匹配司机
                    // 查询当前车辆信息
                    QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("id",carId);

                    // 查询当前司机信息
                    orderInfo.setDriverId(driverId);
                    orderInfo.setDriverPhone(driverPhone);
                    orderInfo.setCarId(carId);

                    orderInfo.setReceiveOrderCarLongitude(longitude);
                    orderInfo.setReceiveOrderCarLatitude(latitude);

                    orderInfo.setReceiveOrderTime(LocalDateTime.now());
                    orderInfo.setLicenseId(licenseId);
                    orderInfo.setVehicleNo(vehicleNo);
                    orderInfo.setOrderStatus(OrderConstants.DRIVER_RECEIVE_ORDER);

                    orderInfoMapper.updateById(orderInfo);

                    break radius;
                }
            }

            // 根据解析出来的终端查询车辆的信息

            // 根据信息找到符合的车辆，进行派单

            // 如果派单成功，则退出循环
        }
    }

    private boolean isPriceRuleExists(OrderRequest orderRequest){
        String fareType = orderRequest.getFareType();
        int i = fareType.indexOf("$");
        String cityCode = fareType.substring(0, i);
        String vehicleType = fareType.substring(i+1,fareType.length());
        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);
        ResponseResult<Boolean> exists = servicePriceClient.isExists(priceRule);
        return exists.getData();
    }

    /**
     * 判断是否黑名单设备
     * @param orderRequest
     * @return
     */
    private boolean isBlackDevice(OrderRequest orderRequest) {
        String deviceCode = orderRequest.getDeviceCode();
        // 生成key
        String deviceCodeKey = RedisPrefixUtils.blackDeviceCodePrefix + deviceCode;
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
    private int isPassengerOrderGoingOn(Long passengerId){
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

    /**
     * 是否有 业务中的订单
     * @param driverId
     * @return
     */
    private int isDriverOrderGoingOn(Long driverId){
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_Id",driverId);
        queryWrapper.and(wrapper->wrapper.eq("order_status",OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status",OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status",OrderConstants.PICK_UP_PASSENGER));
        return orderInfoMapper.selectCount(queryWrapper);
    }
}
