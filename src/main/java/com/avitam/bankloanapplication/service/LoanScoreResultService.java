package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanScoreResultDto;
import com.avitam.bankloanapplication.model.entity.LoanScoreResult;
import com.avitam.bankloanapplication.repository.LoanScoreResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class LoanScoreResultService {
    private static final String ADMIN_LOANSCORE = "/admin/loanScoreResult";
    @Autowired
    private LoanScoreResultRepository loanScoreResultRepository;

    @Autowired
    private ModelMapper modelMapper;

    public LoanScoreResultDto createLoanScore(LoanScoreResultDto request) {
        LoanScoreResultDto loanScoreResultDto = null;
        LoanScoreResult loanScoreResult = null;
        if(request.getRecordId()!=null){
            LoanScoreResult requestData = modelMapper.map(request, LoanScoreResult.class);
            loanScoreResult= loanScoreResultRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, loanScoreResult);
            loanScoreResultRepository.save(loanScoreResult);
        }
        else {
            loanScoreResult = modelMapper.map(request, LoanScoreResult.class);
            loanScoreResultRepository.save(loanScoreResult);
        }
        if(request.getRecordId()==null){
            loanScoreResult.setRecordId(String.valueOf(loanScoreResult.getId().getTimestamp()));
        }
        loanScoreResultRepository.save(loanScoreResult);
        loanScoreResultDto=modelMapper.map(loanScoreResult, LoanScoreResultDto.class);
        loanScoreResultDto.setBaseUrl(ADMIN_LOANSCORE);
        return loanScoreResultDto;

    }
}
