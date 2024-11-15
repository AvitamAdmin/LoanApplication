package com.avitam.bankloanapplication.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import jakarta.validation.constraints.Size;

@Data
public class UserDataDTO {
    @Size(min = 5, max = 15)
    private String username;

    @NotBlank
    @Email(message = "Email not valid")
    private String email;

    @Size(min = 5, max = 20)
    private String password;
}

