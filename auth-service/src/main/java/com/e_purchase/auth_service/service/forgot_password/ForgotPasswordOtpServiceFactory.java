package com.e_purchase.auth_service.service.forgot_password;

import com.e_purchase.auth_service.enums.OtpType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForgotPasswordOtpServiceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordOtpServiceFactory.class);

    private final ForgotPasswordOtpEmailService forgotPasswordOtpEmailService;
    private final ForgotPasswordOtpSmsService forgotPasswordOtpSmsService;

    public ForgotPasswordOtpService resolve(OtpType otpType) {
        return switch (otpType) {
            case EMAIL -> forgotPasswordOtpEmailService;
            case MOBILE_NUMBER -> forgotPasswordOtpSmsService;
            default -> {
                LOGGER.error("Invalid OTP type: {}", otpType);
                yield null;
            }
        };
    }
}
