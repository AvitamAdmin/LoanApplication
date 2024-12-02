package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanStatusWsDto;

public interface LoanStatusService {

    LoanStatusWsDto handleEdit(LoanStatusWsDto request);
}
