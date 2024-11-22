package com.avitam.bankloanapplication.model.dto;

import com.avitam.bankloanapplication.model.entity.LoanLimit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoanLimitWsDto extends CommonWsDto{

    private List<LoanLimitDto> loanLimitDtos;
}
