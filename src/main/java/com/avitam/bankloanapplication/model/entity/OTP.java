package com.avitam.bankloanapplication.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("OTP")
public class OTP extends BaseEntity {
    private String userId;

    private String mobileOtp;

    private String emailOtp;
}
