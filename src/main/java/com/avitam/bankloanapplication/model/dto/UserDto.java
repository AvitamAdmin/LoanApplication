package com.avitam.bankloanapplication.model.dto;

import com.avitam.bankloanapplication.model.entity.Role;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto extends CommonDto {
    private String username;
    private String email;
    private String password;


}

