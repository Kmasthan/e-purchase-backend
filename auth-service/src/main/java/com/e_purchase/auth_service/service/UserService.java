package com.e_purchase.auth_service.service;

import com.e_purchase.auth_service.Entity.UserInfo;
import com.e_purchase.auth_service.dto.UserLogin;

public interface UserService {
    void createUser(UserInfo userInfo);

    String login(UserLogin userLogin);
}
