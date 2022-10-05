package com.lxf.apipassenger.request;

import lombok.Data;

@Data
public class VerificationCodeDTO {


    public String passengerPhone;

    public String verificationCode;
}
