package com.avitam.bankloanapplication.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoanLimit extends BaseEntity{

    private String description;
    private Double loanLimitAmount;
    private Double incomeLimit;

}
