package com.mbj.internalcommmon.response;

import lombok.Data;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-30 23:28
 * @Description:
 * @Version:
 */
@Data
public class OrderDriverResponse {

    private Long driverId;

    private String driverPhone;

    private Long carId;

    /**
     * 机动车驾驶证号
     */
    private String licenseId;

    /**
     * 车辆号牌
     */
    private String vehicleNo;

    /**
     * 车辆类型
     */
    private String vehicleType;

}
