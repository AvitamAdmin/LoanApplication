package com.avitam.bankloanapplication.model.dto;

import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoanScoreResultDto extends CommonDto{

    private String description;
    private Integer loanScoreLimit;
}
