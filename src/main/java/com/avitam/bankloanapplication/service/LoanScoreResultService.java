package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanScoreResultWsDto;

public interface LoanScoreResultService {
    LoanScoreResultWsDto createLoanScore(LoanScoreResultWsDto request);
}
