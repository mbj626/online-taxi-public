package com.mbj.internalcommmon.constant;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-28 20:10
 * @Description:
 * @Version:
 */
public class DriverConstant {

    /**
     * 司机车辆关系状态：绑定
     */
    public static final int DRIVER_CAR_BIND = 1;

    /**
     * 司机车辆关系状态：解绑
     */
    public static final int DRIVER_CAR_UNBIND = 2;

    /**
     * 司机状态：有效
     */
    public static final int DRIVER_STATE_VALID = 1;

    /**
     * 司机状态：失效
     */
    public static final int DRIVER_STATE_INVALID = 0;

    /**
     * 司机状态：存在
     */
    public static final int DRIVER_EXISTS = 1;

    /**
     * 司机状态：不存在
     */
    public static final int DRIVER_NOT_EXISTS = 0;

    /**
     * 司机工作状态：收车
     */
    public static final int DRIVER_WORK_STATUS_STOP = 0;

    /**
     * 司机工作状态：出车
     */
    public static final int DRIVER_WORK_STATUS_START = 1;

    /**
     * 司机工作状态：暂停
     */
    public static final int DRIVER_WORK_STATUS_SUSPEND = 2;


}
