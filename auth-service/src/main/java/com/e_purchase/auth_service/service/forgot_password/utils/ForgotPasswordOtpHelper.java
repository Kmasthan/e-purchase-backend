package com.e_purchase.auth_service.service.forgot_password.utils;

import com.e_purchase.auth_service.configuration.utils.PasswordEncoderUtil;
import com.e_purchase.auth_service.dto.ForgotPasswordRequestDto;
import com.e_purchase.auth_service.entity.PasswordResetOtp;
import com.e_purchase.auth_service.entity.UserInfo;
import com.e_purchase.auth_service.repository.PasswordResetOtpRepository;
import com.e_purchase.auth_service.repository.UserServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ForgotPasswordOtpHelper {

    private static final int OTP_VALID_MINUTES = 2;
    private final UserServiceRepository userServiceRepository;
    private final PasswordResetOtpRepository passwordResetOtpRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;
    private final SecureRandom random = new SecureRandom();

    public Optional<UserInfo> findUser(String identifier) {
        return userServiceRepository.findByEmailOrPhoneNumber(identifier, identifier);
    }

    public String generateAndSaveOtp(UserInfo user, ForgotPasswordRequestDto dto) {
        String otp = String.format("%06d", random.nextInt(1_000_000));
        PasswordResetOtp passwordResetOtp = new PasswordResetOtp();
        passwordResetOtp.setUserId(user.getId());
        passwordResetOtp.setOtp(passwordEncoderUtil.encodePassword(otp));
        passwordResetOtp.setOtpType(dto.getOtpType());
        passwordResetOtp.setAttemptCount(0);
        passwordResetOtp.setExpiryTime(LocalDateTime.now().plusMinutes(OTP_VALID_MINUTES));
        passwordResetOtpRepository.save(passwordResetOtp);
        return otp;
    }

    public int getOtpValidMinutes() {
        return OTP_VALID_MINUTES;
    }
}
