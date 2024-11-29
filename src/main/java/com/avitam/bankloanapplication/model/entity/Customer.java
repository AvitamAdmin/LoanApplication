package com.avitam.bankloanapplication.model.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("customer")

public class Customer extends BaseEntity{
    private String nationalIdentityNumber;

    private String firstName;

    private String lastName;

    private Double monthlyIncome;

    private String gender;

    private Integer age;

    private String phone;

    private String email;

    private String password;

    private Set<Role> roles;

    private Integer loanScore = 500;

    private List<String> loanApplicationId;

    private List<String> customerList;

}
