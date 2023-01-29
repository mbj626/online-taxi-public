package com.mbj.internalcommmon.request;

import lombok.Data;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 14:50
 * @Description:
 * @Version:
 */
@Data
public class PointRequest {

    private String tid;

    private String trid;

    private PointDTO[] points;

}
