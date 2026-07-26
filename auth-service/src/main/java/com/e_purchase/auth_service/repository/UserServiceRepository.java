package com.e_purchase.auth_service.repository;

import com.e_purchase.auth_service.entity.UserInfo;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserServiceRepository extends JpaRepository<@NonNull UserInfo, @NonNull Long> {
    Optional<UserInfo> findByUserNameOrEmailOrPhoneNumber(String userName, String email, String phoneNumber);
    
    Optional<UserInfo> findByEmailOrPhoneNumber(String identifier, String identifier1);
}
