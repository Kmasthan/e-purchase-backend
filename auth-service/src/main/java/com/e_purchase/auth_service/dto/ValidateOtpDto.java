package com.e_purchase.auth_service.dto;

import com.e_purchase.auth_service.enums.OtpType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ValidateOtpDto {
    private Long userId;
    private String otp;
    private OtpType otpType;
}
