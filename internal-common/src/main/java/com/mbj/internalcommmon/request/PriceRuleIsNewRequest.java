package com.mbj.internalcommmon.request;

import lombok.Data;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-31 00:05
 * @Description:
 * @Version:
 */
@Data
public class PriceRuleIsNewRequest {

    private String fareType;

    private Integer fareVersion;

}
