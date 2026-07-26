package com.e_purchase.auth_service.service.forgot_password;

import com.e_purchase.auth_service.configuration.utils.PasswordEncoderUtil;
import com.e_purchase.auth_service.dto.ValidateOtpDto;
import com.e_purchase.auth_service.entity.PasswordResetOtp;
import com.e_purchase.auth_service.exception.UserException;
import com.e_purchase.auth_service.repository.PasswordResetOtpRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForgotPasswordOtpValidationSservice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordOtpValidationSservice.class);
    private static final String OTP_VALIDATION_FAILED = "OTP validation failed";
    private static final int MAX_VALIDATION_ATTEMPT_COUNT = 5;

    private final PasswordResetOtpRepository passwordResetOtpRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;

    public void validate(ValidateOtpDto validateOtpDto) {
        LOGGER.info("Validating OTP for user: {}", validateOtpDto.getUserId());
        Optional<PasswordResetOtp> otpDataOpt = passwordResetOtpRepository.findByUserIdAndOtpTypeAndExpiryTimeAfter(validateOtpDto.getUserId(), validateOtpDto.getOtpType(), LocalDateTime.now());
        if (otpDataOpt.isEmpty()) {
            LOGGER.error("OTP data not found for user Id : {}", validateOtpDto.getUserId());
            throw new UserException(OTP_VALIDATION_FAILED);
        }
        PasswordResetOtp otpData = otpDataOpt.get();
        checkOtpValidationAttemptCount(otpData);
        updateOtpValidationAttemptsCount(otpData);
        if (!passwordEncoderUtil.matches(validateOtpDto.getOtp(), otpData.getOtp())) {
            LOGGER.error("User '{}' entered invalid OTP", validateOtpDto.getUserId());
            throw new UserException(OTP_VALIDATION_FAILED);
        }
        deleteExistingOtpData(otpData);
    }

    private void checkOtpValidationAttemptCount(PasswordResetOtp otpData) {
        if (otpData.getAttemptCount() >= MAX_VALIDATION_ATTEMPT_COUNT) {
            LOGGER.error("OTP validation attempts exceeded for user Id : {}", otpData.getUserId());
            deleteExistingOtpData(otpData);
            throw new UserException(OTP_VALIDATION_FAILED);
        }
    }

    private void updateOtpValidationAttemptsCount(PasswordResetOtp otpData) {
        LOGGER.info("Updating OTP validation attempt count for user Id : '{}', new count : '{}'", otpData.getUserId(), otpData.getAttemptCount() + 1);
        otpData.setAttemptCount(otpData.getAttemptCount() + 1);
        passwordResetOtpRepository.save(otpData);
    }

    private void deleteExistingOtpData(PasswordResetOtp otpData) {
        LOGGER.info("Deleting existing OTP data for user Id : '{}'", otpData.getUserId());
        passwordResetOtpRepository.deleteById(otpData.getUserId());
    }
}
