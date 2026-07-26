package com.e_purchase.auth_service.service;

import com.e_purchase.auth_service.dto.ForgotPasswordRequestDto;
import com.e_purchase.auth_service.dto.ValidateOtpDto;
import com.e_purchase.auth_service.entity.UserInfo;
import com.e_purchase.auth_service.configuration.utils.PasswordEncoderUtil;
import com.e_purchase.auth_service.dto.UserLogin;
import com.e_purchase.auth_service.exception.InvalidPasswordException;
import com.e_purchase.auth_service.exception.UserException;
import com.e_purchase.auth_service.exception.UserNotFoundException;
import com.e_purchase.auth_service.repository.UserServiceRepository;
import com.e_purchase.auth_service.service.forgot_password.ForgotPasswordOtpService;
import com.e_purchase.auth_service.service.forgot_password.ForgotPasswordOtpServiceFactory;
import com.e_purchase.auth_service.service.forgot_password.ForgotPasswordOtpValidationSservice;
import jakarta.mail.MessagingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String USER_NAME_NULL_OR_EMPTY = "User name is null or empty";
    private static final String USER_EMAIL_NULL_OR_EMPTY = "User email is null or empty";
    private static final String USER_TYPE_NULL = "User type is null";
    private static final String USER_PASSWORD_NULL_OR_EMPTY = "User password is null or empty";
    private static final String USER_PHONE_NULL_OR_EMPTY = "User phone number is null or empty";
    private static final String LOGIN_PASSWORD_NULL_OR_EMPTY = "Password is null or empty in login request";
    private static final String LOGIN_IDENTIFIER_NULL_OR_EMPTY = "User name is null or empty in login request";
    private static final String USER_ID_IS_NULL_OR_INVALID = "User Id is null or invalid";
    private static final String OTP_IS_NULL_OR_EMPTY = "OTP is null or empty";
    private static final String OTP_TYPE_IS_NULL = "OTP type is null";
    private static final String OTP_VALIDATION_FAILED = "OTP validation failed";

    private final UserServiceRepository userServiceRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;
    private final JwtTokenService jwtTokenService;
    private final ForgotPasswordOtpServiceFactory forgotPasswordOtpServiceFactory;
    private final ForgotPasswordOtpValidationSservice forgotPasswordOtpValidationSservice;

    @Override
    public void createUser(UserInfo userInfo) {
        if (!validateUserInfo(userInfo)) {
            LOGGER.info("User info validation failed for user: {}", userInfo);
            throw new UserException("Invalid user info provided");
        }
        userInfo.setCreatedBy(userInfo.getUserName());
        userInfo.setLastModifiedBy(userInfo.getUserName());
        userInfo.setPassword(passwordEncoderUtil.encodePassword(userInfo.getPassword()));
        this.userServiceRepository.save(userInfo);
        LOGGER.info("User created successfully for: {}", userInfo.getUserName());
    }

    @Override
    public String login(UserLogin userLogin) {
        if (!validateUserLogin(userLogin)) {
            LOGGER.info("User login validation failed for username: {}", userLogin.getIdentifier());
            throw new UserException("Invalid login request");
        }
        String loginIdentifier = userLogin.getIdentifier().trim();
        UserInfo userInfo = userServiceRepository.findByUserNameOrEmailOrPhoneNumber(loginIdentifier, loginIdentifier, loginIdentifier)
                .orElseThrow(() -> {
                    LOGGER.error("User not found for: {}", loginIdentifier);
                    return new UserNotFoundException("Invalid Username or Email or Phone Number");
                });
        if (!passwordEncoderUtil.matches(userLogin.getPassword(), userInfo.getPassword())) {
            LOGGER.error("Invalid password for username: {}", loginIdentifier);
            throw new InvalidPasswordException("Invalid password");
        }
        LOGGER.info("User logged in successfully: {}", loginIdentifier);
        return jwtTokenService.generateAuthenticationToken(userInfo);
    }

    @Override
    public String forgotPassword(@NonNull ForgotPasswordRequestDto forgotPasswordRequestDto) throws MessagingException {
        ForgotPasswordOtpService otpService = forgotPasswordOtpServiceFactory.resolve(forgotPasswordRequestDto.getOtpType());
        if (otpService == null) return "Invalid OTP type";
        otpService.send(forgotPasswordRequestDto);
        return "OTP sent";
    }

    @Override
    public String validateOtp(@NonNull ValidateOtpDto validateOtpDto) {
        if (!validateOtpValidationData(validateOtpDto)) {
            LOGGER.error("Invalid otp details");
            throw new UserException(OTP_VALIDATION_FAILED);
        }
        forgotPasswordOtpValidationSservice.validate(validateOtpDto);
        return "OTP Validated";
    }

    private boolean validateUserInfo(UserInfo userInfo) {
        if (Objects.isNull(userInfo.getUserName()) || userInfo.getUserName().isEmpty()) {
            LOGGER.error(USER_NAME_NULL_OR_EMPTY);
            return false;
        }
        if (Objects.isNull(userInfo.getEmail()) || userInfo.getEmail().isEmpty()) {
            LOGGER.error(USER_EMAIL_NULL_OR_EMPTY);
            return false;
        }
        if (Objects.isNull(userInfo.getUserType())) {
            LOGGER.error(USER_TYPE_NULL);
            return false;
        }
        if (Objects.isNull(userInfo.getPassword()) || userInfo.getPassword().isEmpty()) {
            LOGGER.error(USER_PASSWORD_NULL_OR_EMPTY);
            return false;
        }
        if (Objects.isNull(userInfo.getPhoneNumber()) || userInfo.getPhoneNumber().isEmpty()) {
            LOGGER.error(USER_PHONE_NULL_OR_EMPTY);
            return false;
        }
        return true;
    }

    private boolean validateUserLogin(UserLogin userLogin) {
        if (Objects.isNull(userLogin.getIdentifier()) || userLogin.getIdentifier().isEmpty()) {
            LOGGER.error(LOGIN_IDENTIFIER_NULL_OR_EMPTY);
            return false;
        }
        if (Objects.isNull(userLogin.getPassword()) || userLogin.getPassword().isEmpty()) {
            LOGGER.error(LOGIN_PASSWORD_NULL_OR_EMPTY);
            return false;
        }
        return true;
    }

    private boolean validateOtpValidationData(ValidateOtpDto validateOtpDto) {
        if (Objects.isNull(validateOtpDto.getUserId()) || validateOtpDto.getUserId() == 0) {
            LOGGER.error(USER_ID_IS_NULL_OR_INVALID);
            return false;
        }
        if (Objects.isNull(validateOtpDto.getOtp()) || validateOtpDto.getOtp().isEmpty()) {
            LOGGER.error(OTP_IS_NULL_OR_EMPTY);
            return false;
        }
        if (Objects.isNull(validateOtpDto.getOtpType())) {
            LOGGER.error(OTP_TYPE_IS_NULL);
            return false;
        }
        return true;
    }
}
