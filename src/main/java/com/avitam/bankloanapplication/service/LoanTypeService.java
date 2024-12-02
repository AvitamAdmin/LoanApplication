package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanTypeWsDto;

public interface LoanTypeService {

    LoanTypeWsDto handleEdit(LoanTypeWsDto request);
}
