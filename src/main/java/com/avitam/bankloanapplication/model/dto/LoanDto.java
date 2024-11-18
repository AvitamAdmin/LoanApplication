package com.avitam.bankloanapplication.model.dto;

import com.avitam.bankloanapplication.model.entity.Loan;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class LoanDto extends CommonDto{
    private Loan loan;
    private List<Loan> loanList;
}
