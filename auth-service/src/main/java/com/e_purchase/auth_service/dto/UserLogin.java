package com.e_purchase.auth_service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserLogin {
    private String identifier;
    private String password;
}
