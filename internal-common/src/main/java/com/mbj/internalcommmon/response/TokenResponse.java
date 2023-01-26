package com.mbj.internalcommmon.response;

import lombok.Data;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 16:30
 * @Description:
 * @Version:
 */
@Data
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

}
