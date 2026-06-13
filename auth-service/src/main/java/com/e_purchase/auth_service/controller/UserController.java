package com.e_purchase.auth_service.controller;

import com.e_purchase.auth_service.Entity.UserInfo;
import com.e_purchase.auth_service.dto.UserLogin;
import com.e_purchase.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/create")
    public ResponseEntity<String> createUser(@RequestBody UserInfo userInfo) {
        userService.createUser(userInfo);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLogin userLogin) {
        return ResponseEntity.ok(userService.login(userLogin));
    }
}
