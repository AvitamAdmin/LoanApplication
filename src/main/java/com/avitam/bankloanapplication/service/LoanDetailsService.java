package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanDetailsWsDto;
import com.avitam.bankloanapplication.model.dto.LoanWsDto;
import com.avitam.bankloanapplication.model.entity.LoanDetails;

public interface LoanDetailsService {

    LoanDetailsWsDto createLoan(LoanDetailsWsDto request);
}
