package com.mbj.internalcommmon.response;

import lombok.Data;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-28 21:56
 * @Description:
 * @Version:
 */
@Data
public class DriverUserExistsResponse {

    private String driverPhone;

    private int isExists;

}
