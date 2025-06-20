package com.avitam.bankloanapplication.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("customer")

public class Customer extends BaseEntity {

    private String fullName;
    private Double monthlyIncome;
    private String gender;
    private Integer age;
    private String phone;
    private String address;
    private String email;
    private String referenceContacts;
    private String panNumber;
    private String aadhaarNumber;
    private String dateOfBirth;
    private String password;
    private String profileImage;
    private Set<Role> roles;
    private Integer loanScore = 500;
    private String resetPasswordToken;
    private String employmentType;
    private String bankAccNumber;
    private String bankIFSCCode;
    private String companyName;
    private String residenceType;
    private String workExperience;
    private String currentEMI;
    private String referralCode;
    private String otp;
    private Integer profileVerificationIndex = 1;
    private boolean profileVerificationStatus;


}
