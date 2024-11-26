package com.avitam.bankloanapplication.model.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomerDto extends CommonDto{

    private String nationalIdentityNumber;

    private String firstName;

    private String lastName;

    private Double monthlyIncome;

    private String gender;

    private Integer age;

    private String phone;

    private String email;

    private String password;

    private Integer loanScore = 500;

    private List<String> loanApplicationId;

    private List<String> customerList;


}
