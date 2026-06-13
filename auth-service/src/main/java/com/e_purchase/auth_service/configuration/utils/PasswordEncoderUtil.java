package com.e_purchase.auth_service.configuration.utils;

import com.e_purchase.auth_service.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoderUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncoderUtil.class);
    private static final String PASSWORD_NULL_OR_EMPTY = "Password cannot be null or empty";
    private static final String RAW_PASSWORD_NULL_OR_EMPTY = "Raw password cannot be null or empty";
    private final PasswordEncoder passwordEncoder;

    public String encodePassword(String password) {
        if (password == null || password.isEmpty()) {
            LOGGER.error("Attempted to encode a null or empty password");
            throw new UserException(PASSWORD_NULL_OR_EMPTY);
        }
        return passwordEncoder.encode(password);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            LOGGER.error("Attempted to match a null or empty raw password");
            throw new UserException(RAW_PASSWORD_NULL_OR_EMPTY);
        }
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
