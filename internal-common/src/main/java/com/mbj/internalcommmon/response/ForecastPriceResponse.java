package com.mbj.internalcommmon.response;

import lombok.Data;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-26 20:19
 * @Description:
 * @Version:
 */
@Data
public class ForecastPriceResponse {
    private double price;
    private String cityCode;
    private String vehicleType;
    private String fareType;
    private Integer fareVersion;
}
