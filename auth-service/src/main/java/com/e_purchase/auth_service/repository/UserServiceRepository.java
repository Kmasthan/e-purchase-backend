package com.e_purchase.auth_service.repository;

import com.e_purchase.auth_service.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserServiceRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUserNameOrEmailOrPhoneNumber(String userName, String email, String phoneNumber);
}
