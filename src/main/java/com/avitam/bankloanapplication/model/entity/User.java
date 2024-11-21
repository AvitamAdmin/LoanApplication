package com.avitam.bankloanapplication.model.entity;

import com.avitam.bankloanapplication.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("users")
public class User extends BaseEntity{
    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
    private String mobileNumber;
    private String resetPasswordToken;

}
