package com.avitam.bankloanapplication.model.dto;

import lombok.*;
import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@ToString
public class LoanDto extends CommonDto{
    private String loanTypeId;
    private Double loanAmount;
    private String loanScoreResultId;
    private String loanStatusId;
    private Date loanDate;
    private Integer creditMultiplier = 4;
    private String loanApplicationId;
    private String loanStatus;
    private String loanScoreResult;
    private String loanType;


}
