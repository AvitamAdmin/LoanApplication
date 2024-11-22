package com.avitam.bankloanapplication.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoanScoreResultDto extends CommonDto{

    private String description;
    private Integer loanScoreLimit;
}
