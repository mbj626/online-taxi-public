package com.mbj.servicedriveruser.controller;

import com.mbj.internalcommmon.constant.DriverConstant;
import com.mbj.internalcommmon.dto.DriverUser;
import com.mbj.internalcommmon.dto.ResponseResult;
import com.mbj.internalcommmon.response.DriverUserExistsResponse;
import com.mbj.internalcommmon.response.OrderDriverResponse;
import com.mbj.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * (DriverUser)表控制层
 *
 * @author makejava
 * @since 2023-01-28 16:33:37
 */
@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    /**
     * 新增司机
     * @param driverUser
     * @return
     */
    @PostMapping("/user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser){
        driverUserService.addDriverUser(driverUser);
        return ResponseResult.success();
    }

    /**
     * 修改司机
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser){
        driverUserService.updateDriverUser(driverUser);
        return ResponseResult.success();
    }

    /**
     * 查询司机
     * @param driverPhone
     * @return
     */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResponse> getDriverUser(@PathVariable("driverPhone") String driverPhone){
        ResponseResult<DriverUser> driverUserByPhone = driverUserService.getDriverUserByPhone(driverPhone);
        int isExists = DriverConstant.DRIVER_EXISTS;
        DriverUserExistsResponse driverUserExistsResponse = new DriverUserExistsResponse();
        if (driverUserByPhone.getData() == null){
            isExists = DriverConstant.DRIVER_NOT_EXISTS;
            driverUserExistsResponse.setDriverPhone(driverPhone);
        }else {
            driverUserExistsResponse.setDriverPhone(driverUserByPhone.getData().getDriverPhone());
        }
        driverUserExistsResponse.setIsExists(isExists);
        return ResponseResult.success(driverUserExistsResponse);
    }

    /**
     * 根据车辆ID查询订单所需要的司机信息
     * @param carId
     * @return
     */
    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId){
        return driverUserService.getAvailableDriver(carId);
    }

}

