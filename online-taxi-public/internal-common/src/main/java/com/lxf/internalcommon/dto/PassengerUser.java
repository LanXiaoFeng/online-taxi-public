package com.lxf.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PassengerUser {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private LocalDateTime gmtCreate;
    /**
     *
     */
    private LocalDateTime gmtModified;
    /**
     *
     */
    private String passengerPhone;
    /**
     *
     */
    private String passengerName;
    /**
     * 0：未知，1：男，2：女
     */
    private byte passengerGender;
    /**
     * 0：有效，1：失效
     */
    private byte state;
    /**
     * 头像图片地址的url
     */
    private String profilePhoto;
}
