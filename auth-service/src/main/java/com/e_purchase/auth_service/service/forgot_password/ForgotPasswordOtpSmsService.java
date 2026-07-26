package com.e_purchase.auth_service.service.forgot_password;

import com.e_purchase.auth_service.dto.ForgotPasswordRequestDto;
import com.e_purchase.auth_service.entity.UserInfo;
import com.e_purchase.auth_service.service.SmsService;
import com.e_purchase.auth_service.service.forgot_password.utils.ForgotPasswordOtpHelper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForgotPasswordOtpSmsService implements ForgotPasswordOtpService {

    private final ForgotPasswordOtpHelper otpHelper;
    private final SmsService smsService;

    @Override
    @Transactional
    public void send(ForgotPasswordRequestDto dto) throws MessagingException {
        Optional<UserInfo> userOpt = otpHelper.findUser(dto.getIdentifier());
        if (userOpt.isEmpty()) return;
        UserInfo user = userOpt.get();
        String otp = otpHelper.generateAndSaveOtp(user, dto);
        String message = "Your epurchase password reset OTP is: " + otp
                + ". Valid for " + otpHelper.getOtpValidMinutes() + " minutes. Do not share it with anyone.";
        smsService.sendSms(user.getPhoneNumber(), message);
    }
}
