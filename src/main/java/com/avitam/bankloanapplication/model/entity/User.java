package com.avitam.bankloanapplication.model.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document("users")
public class User extends BaseEntity{
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
    private String mobileNumber;
    private String resetPasswordToken;

}
