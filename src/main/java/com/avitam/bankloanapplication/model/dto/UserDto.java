package com.avitam.bankloanapplication.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto extends CommonDto{

    private String email;
    private String password;
    private String username;

}

