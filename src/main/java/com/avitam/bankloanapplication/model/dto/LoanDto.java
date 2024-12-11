package com.avitam.bankloanapplication.model.dto;

import com.avitam.bankloanapplication.model.entity.Loan;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class LoanDto extends CommonDto{
    private String loanTypeId;
    private Double loanLimit;
    private String loanScoreResultId;
    private String loanStatusId;
    private Date loanDate;
    private Integer creditMultiplier = 4;
    private String loanApplicationId;
    private String loanStatus;
    private String loanScoreResult;

}
