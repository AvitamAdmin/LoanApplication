package com.avitam.bankloanapplication.model.entity;

import com.avitam.bankloanapplication.model.Role;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document( "users")
public class User {

    private Integer id;

    @Size(min = 5, max = 25, message = "username length should be between 5 and 25 characters")
    private String username;

    private String email;

    @Size(min = 5, message = "Minimum password length: 5 characters")
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    private List<Role> roles;
    private String mobileNumber;
    private String resetPasswordToken;
}
