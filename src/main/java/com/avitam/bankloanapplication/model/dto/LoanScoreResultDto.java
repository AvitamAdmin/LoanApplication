package com.avitam.bankloanapplication.model.dto;

import com.avitam.bankloanapplication.model.entity.LoanScoreResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoanScoreResultDto extends CommonDto{
    private LoanScoreResult loanScoreResult;
    private List<LoanScoreResult> loanScoreResultList;
}
