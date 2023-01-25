package com.mbj.apipassenger.request;

import lombok.Data;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 14:23
 * @Description:
 * @Version:
 */
@Data
public class VerificationCodeDTO {

    private String passengerPhone;

    private String verificationCode;

}
