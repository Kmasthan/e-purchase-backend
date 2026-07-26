package com.e_purchase.auth_service.repository;

import com.e_purchase.auth_service.entity.PasswordResetOtp;
import com.e_purchase.auth_service.enums.OtpType;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetOtpRepository extends JpaRepository<@NonNull PasswordResetOtp, @NonNull Long> {

    @Transactional
    void deleteByExpiryTimeBefore(LocalDateTime expiryTime);

    Optional<PasswordResetOtp> findByUserIdAndOtpTypeAndExpiryTimeAfter(Long userId, OtpType otpType, LocalDateTime currentDateWithTime);
}
