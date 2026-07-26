package com.e_purchase.auth_service.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromNumber;

    @PostConstruct
    private void init() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String toNumber, String message) {
        Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber), message).create();
    }
}
