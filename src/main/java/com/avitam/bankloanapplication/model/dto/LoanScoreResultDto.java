package com.avitam.bankloanapplication.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class LoanScoreResultDto extends CommonDto{

    private String description;
    private int loanScoreLimit;
    private List<String> loanScoreResultList;
}
