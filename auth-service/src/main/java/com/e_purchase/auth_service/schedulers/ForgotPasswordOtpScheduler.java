package com.e_purchase.auth_service.schedulers;

import com.e_purchase.auth_service.repository.PasswordResetOtpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ForgotPasswordOtpScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordOtpScheduler.class);
    private final PasswordResetOtpRepository passwordResetOtpRepository;

    public ForgotPasswordOtpScheduler(PasswordResetOtpRepository passwordResetOtpRepository) {
        this.passwordResetOtpRepository = passwordResetOtpRepository;
    }

    @Scheduled(cron = "0 59 23 * * *")
    private void flushExpiredForgotPasswordOtps() {
        try {
            LOGGER.info("Deleting expired forgot password OTPs");
            passwordResetOtpRepository.deleteByExpiryTimeBefore(LocalDateTime.now().minusMinutes(2));
        } catch (Exception e) {
            LOGGER.error("Error occurred while deleting expired forgot password OTPs", e);
        }
    }
}
