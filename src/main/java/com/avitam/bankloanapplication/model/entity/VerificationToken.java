package com.avitam.bankloanapplication.model.entity;


import com.avitam.bankloanapplication.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document("VerificationToken")
public class VerificationToken  {

    private int id;
    private String userId;
    private String token;
    private int status;
    public User user;
    private Date ExpiryDate;
}
