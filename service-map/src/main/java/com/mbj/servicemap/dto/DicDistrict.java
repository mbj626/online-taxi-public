package com.mbj.servicemap.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * (DicDistrict)实体类
 *
 * @author makejava
 * @since 2023-01-27 22:25:44
 */
@Data
public class DicDistrict{
    /**
     * 地区编号
     */
    private String addressCode;
    /**
     * 地区名称
     */
    private String addressName;
    /**
     * 父地区名称
     */
    private String parentAddressCode;
    /**
     * 级别
     */
    private Integer level;

}

