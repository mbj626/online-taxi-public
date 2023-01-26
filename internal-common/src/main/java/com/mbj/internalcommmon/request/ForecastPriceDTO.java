package com.mbj.internalcommmon.request;

import lombok.Data;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-26 20:13
 * @Description:
 * @Version:
 */
@Data
public class ForecastPriceDTO {

    private String depLongitude;

    private String depLatitude;

    private String destLongitude;

    private String destLatitude;

}
