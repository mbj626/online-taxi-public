
package com.mbj.internalcommmon.response;

import lombok.Data;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-29 14:00
 * @Description:
 * @Version:
 */
@Data
public class TerminalResponse {

    private String tid;

    private Long carId;

    private String longitude;

    private String latitude;
}
