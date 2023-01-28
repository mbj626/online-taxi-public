package com.mbj.internalcommmon.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * (DriverUser)实体类
 *
 * @author makejava
 * @since 2023-01-28 16:30:49
 */
@Data
public class DriverUser implements Serializable{
    /**
     * 司机id
     */
    private Long id;
    /**
     * 司机注册地行政区划代码
     */
    private String address;
    /**
     * 司机姓名
     */
    private String driverName;
    /**
     * 司机手机号
     */
    private String driverPhone;
    /**
     * 司机性别
     */
    private Integer driverGender;
    /**
     * 司机生日
     */
    private LocalDate driverBirthday;
    /**
     * 司机民族
     */
    private String driverNation;
    /**
     * 司机住址
     */
    private String driverContactAddress;
    /**
     * 机动车驾驶证号
     */
    private String licenseId;
    /**
     * 初次领取驾驶证日期
     */
    private LocalDate getDriverLicenseDate;
    /**
     * 驾驶证有效期起始日期
     */
    private LocalDate driverLicenseOn;
    /**
     * 驾驶证有效期终止日期
     */
    private LocalDate driverLicenseOff;
    /**
     * 是否巡游出租汽车：1是，0否
     */
    private Integer taxiDriver;
    /**
     * 网络预约出租汽车驾驶员资格证号
     */
    private String certificateNo;
    /**
     * 网络预约出租汽车驾驶员证发证机构
     */
    private String networkCarIssueOrganization;
    /**
     * 网络预约出租汽车驾驶员证发证日期
     */
    private LocalDate networkCarIssueDate;
    /**
     * 网络预约出租汽车驾驶员证领证日期
     */
    private LocalDate getNetworkCarProofDate;
    /**
     * 网络预约出租汽车驾驶员证起始日期
     */
    private LocalDate networkCarProofOn;
    /**
     * 网络预约出租汽车驾驶员证终止日期
     */
    private LocalDate networkCarProofOff;
    /**
     * 报备日期
     */
    private LocalDate registerDate;
    /**
     * 服务类型：1网络预约出租汽车，2巡游出租汽车3，私人小客车合乘
     */
    private Integer commercialType;
    /**
     * 驾驶员合同（协议）签署公司
     */
    private String contractCompany;
    /**
     * 合同（协议）有效期起
     */
    private LocalDate constarctOn;
    /**
     * 合同（协议）有效期止
     */
    private LocalDate constarctOff;
    /**
     * 状态：0有效，1失效
     */
    private Integer state;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

}

