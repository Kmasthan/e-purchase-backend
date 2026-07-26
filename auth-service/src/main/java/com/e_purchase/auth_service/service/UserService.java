package com.e_purchase.auth_service.service;

import com.e_purchase.auth_service.dto.ForgotPasswordRequestDto;
import com.e_purchase.auth_service.dto.ValidateOtpDto;
import com.e_purchase.auth_service.entity.UserInfo;
import com.e_purchase.auth_service.dto.UserLogin;
import jakarta.mail.MessagingException;
import lombok.NonNull;

public interface UserService {
    void createUser(UserInfo userInfo);

    String login(UserLogin userLogin);

    String forgotPassword(@NonNull ForgotPasswordRequestDto forgotPasswordRequestDto) throws MessagingException;

    String validateOtp(@NonNull ValidateOtpDto validateOtpDto);
}
