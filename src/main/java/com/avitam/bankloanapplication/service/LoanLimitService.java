package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanLimitDto;
import com.avitam.bankloanapplication.model.entity.LoanLimit;
import com.avitam.bankloanapplication.repository.LoanLimitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanLimitService {
    @Autowired
    private LoanLimitRepository loanLimitRepository;
    @Autowired
    private ModelMapper modelMapper;
    private static final String ADMIN_LOANLIMIT = "/admin/loanLimit";

    public LoanLimitDto editLoanLimit(LoanLimitDto request) {
        LoanLimitDto loanLimitDto = new LoanLimitDto();
        LoanLimit loanLimit = null;
        if(request.getRecordId()!=null){
            loanLimit= loanLimitRepository.findByRecordId(request.getRecordId());
            loanLimit=modelMapper.map(request, LoanLimit.class);
            loanLimitRepository.save(loanLimit);
        }
        else {
            loanLimit = modelMapper.map(request, LoanLimit.class);
            loanLimitRepository.save(loanLimit);
        }
        if(request.getRecordId()==null){
            loanLimit.setRecordId(String.valueOf(loanLimit.getId().getTimestamp()));
        }
        loanLimitRepository.save(loanLimit);
        loanLimitDto.setBaseUrl(ADMIN_LOANLIMIT);
        return request;
    }

}
