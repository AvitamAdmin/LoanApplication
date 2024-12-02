package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanWsDto;

public interface LoanService {

    LoanWsDto createLoan(LoanWsDto request);
}
