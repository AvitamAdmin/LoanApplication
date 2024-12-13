package com.avitam.bankloanapplication.service.impl;

import com.avitam.bankloanapplication.model.dto.LoanLimitDto;
import com.avitam.bankloanapplication.model.dto.LoanLimitWsDto;
import com.avitam.bankloanapplication.model.entity.LoanLimit;
import com.avitam.bankloanapplication.repository.LoanLimitRepository;
import com.avitam.bankloanapplication.service.LoanLimitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoanLimitServiceImpl implements LoanLimitService {
    @Autowired
    private LoanLimitRepository loanLimitRepository;
    @Autowired
    private ModelMapper modelMapper;
    private static final String ADMIN_LOANLIMIT = "/loans/loanLimit";

    public LoanLimitWsDto editLoanLimit(LoanLimitWsDto request) {

        LoanLimit loanLimit=new LoanLimit();
        List<LoanLimitDto> loanStatusDtos = request.getLoanLimitDtos();
        List<LoanLimit> loanLimits = new ArrayList<>();
        for(LoanLimitDto loanLimitDto: loanStatusDtos) {

            if (loanLimitDto.getRecordId() != null) {
                loanLimit = loanLimitRepository.findByRecordId(loanLimitDto.getRecordId());
                modelMapper.map(loanLimitDto, loanLimit);
                loanLimitRepository.save(loanLimit);
                request.setMessage("Data updated successfully");
            } else {
                loanLimit = modelMapper.map(loanLimitDto, LoanLimit.class);
                loanLimit.setCreationTime(new Date());
                loanLimit.setStatus(true);
                loanLimitRepository.save(loanLimit);
            }
            if (request.getRecordId() == null) {
                loanLimit.setRecordId(String.valueOf(loanLimit.getId().getTimestamp()));
            }
            loanLimitRepository.save(loanLimit);
            loanLimits.add(loanLimit);
            request.setBaseUrl(ADMIN_LOANLIMIT);
            request.setMessage("Data added Successfully");
        }
        request.setLoanLimitDtos(modelMapper.map(loanLimits,List.class));
        return request;
    }

}
