package com.mbj.servicepassengeruser.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 17:18
 * @Description:
 * @Version:
 */
@Data
public class PassengerUser {

    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String passengerPhone;

    private String passengerName;

    private byte passengerGender;

    private byte state;

}
