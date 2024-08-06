package com.spring.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailOTPBean {
    private String email;
    private String otpCode;
}
