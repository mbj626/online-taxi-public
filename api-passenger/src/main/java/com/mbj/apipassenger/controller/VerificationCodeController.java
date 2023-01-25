package com.mbj.apipassenger.controller;

import com.mbj.apipassenger.request.VerificationCodeDTO;
import com.mbj.apipassenger.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 14:20
 * @Description:
 * @Version:
 */
@RestController
public class VerificationCodeController {

    @Autowired
    VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public String VerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println(passengerPhone);
        return verificationCodeService.generatorCode(passengerPhone);
    }
}
