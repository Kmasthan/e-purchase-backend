package com.e_purchase.auth_service.entity;

import com.e_purchase.auth_service.enums.Gender;
import com.e_purchase.auth_service.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "USERS", schema = "E_PURCHASE_USERS")
@Getter
@Setter
public class UserInfo extends AuditEntity {
    private static final String USER_SEQUENCE_GEN = "user_seq_gen";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = USER_SEQUENCE_GEN)
    @SequenceGenerator(name = USER_SEQUENCE_GEN, sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "USER_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    // Personal Details
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;

    @Column(name = "GENDER")
    private Gender gender;

    // Contact Details
    @Column(name = "PHONE_NUMBER", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "ALTERNATE_PHONE_NUMBER")
    private String alternatePhoneNumber;

    // Address Information
    @Column(name = "ADDRESS_LINE_1")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2")
    private String addressLine2;

    @Column(name = "CITY")
    private String city;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

}
