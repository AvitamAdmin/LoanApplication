package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanLimitDto;
import com.avitam.bankloanapplication.model.dto.LoanLimitWsDto;
import com.avitam.bankloanapplication.model.dto.LoanStatusDto;
import com.avitam.bankloanapplication.model.dto.LoanStatusWsDto;
import com.avitam.bankloanapplication.model.entity.LoanLimit;
import com.avitam.bankloanapplication.model.entity.LoanStatus;
import com.avitam.bankloanapplication.repository.LoanLimitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanLimitService {
    @Autowired
    private LoanLimitRepository loanLimitRepository;
    @Autowired
    private ModelMapper modelMapper;
    private static final String ADMIN_LOANLIMIT = "/admin/loanLimit";

    public LoanLimitWsDto editLoanLimit(LoanLimitWsDto request) {
        LoanLimitWsDto loanLimitWsDto = new LoanLimitWsDto();
        LoanLimit loanLimit=new LoanLimit();
        List<LoanLimitDto> loanStatusDtos = request.getLoanLimitDtos();
        List<LoanLimit> loanLimits = new ArrayList<>();
        for(LoanLimitDto loanLimitDto: loanStatusDtos) {

            if (loanLimitDto.getRecordId() != null) {
                loanLimit = loanLimitRepository.findByRecordId(loanLimitDto.getRecordId());
                modelMapper.map(loanLimitDto, loanLimit);
                loanLimitRepository.save(loanLimit);
            } else {
                loanLimit = modelMapper.map(loanLimitDto, LoanLimit.class);
                loanLimitRepository.save(loanLimit);
            }
            if (request.getRecordId() == null) {
                loanLimit.setRecordId(String.valueOf(loanLimit.getId().getTimestamp()));
            }
            loanLimitRepository.save(loanLimit);
            loanLimits.add(loanLimit);
            request.setBaseUrl(ADMIN_LOANLIMIT);
        }
        request.setLoanLimitDtos(modelMapper.map(loanLimits,List.class));
        return request;
    }

}
