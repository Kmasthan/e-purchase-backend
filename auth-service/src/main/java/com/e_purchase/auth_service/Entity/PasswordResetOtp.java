package com.e_purchase.auth_service.entity;

import com.e_purchase.auth_service.enums.OtpType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "PASSWORD_RESET_OTP", schema = "E_PURCHASE_USERS")
@Getter
@Setter
public class PasswordResetOtp {

    @Id
    @Column(name = "USER_ID", nullable = false, unique = true)
    private Long userId;

    @Column(name = "OTP", nullable = false)
    private String otp;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private OtpType otpType;

    @Column(name = "ATTEMPT_COUNT", nullable = false)
    private int attemptCount;

    @Column(name = "EXPIRY_TIME", nullable = false)
    private LocalDateTime expiryTime;
}
