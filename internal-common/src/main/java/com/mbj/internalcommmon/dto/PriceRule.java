package com.mbj.internalcommmon.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * (PriceRule)实体类
 *
 * @author makejava
 * @since 2023-01-26 22:16:36
 */
@Data
public class PriceRule {

    private String cityCode;
    
    private String vehicleType;
    
    private Double startFare;
    
    private Integer startMile;
    
    private Double unitPricePerMile;
    
    private Double unitPricePerMinute;

}

