package com.e_purchase.auth_service.service.forgot_password;

import com.e_purchase.auth_service.dto.ForgotPasswordRequestDto;
import jakarta.mail.MessagingException;

public interface ForgotPasswordOtpService {
    void send(ForgotPasswordRequestDto dto) throws MessagingException;
}
