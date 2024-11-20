package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanDto;
import com.avitam.bankloanapplication.model.dto.LoanScoreResultDto;
import com.avitam.bankloanapplication.model.entity.Loan;
import com.avitam.bankloanapplication.model.entity.LoanScoreResult;
import com.avitam.bankloanapplication.repository.LoanScoreResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class LoanScoreResultService {
    private static final String ADMIN_LOANSCORE = "/admin/loanscore";
    @Autowired
    private LoanScoreResultRepository loanScoreResultRepository;

    @Autowired
    private ModelMapper modelMapper;
    public LoanScoreResultDto createLoanScore(LoanScoreResultDto request) {
        LoanScoreResultDto loanScoreResultDto = new LoanScoreResultDto();
        LoanScoreResult loanScoreResult = null;
        if(request.getRecordId()!=null){
            LoanScoreResult requestData = request.getLoanScoreResult();
            loanScoreResult= loanScoreResultRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, loanScoreResult);
            loanScoreResultRepository.save(loanScoreResult);
        }
        else {
            loanScoreResult = request.getLoanScoreResult();
            loanScoreResultRepository.save(loanScoreResult);
        }
        if(request.getRecordId()==null){
            loanScoreResult.setRecordId(String.valueOf(loanScoreResult.getId().getTimestamp()));
        }
        loanScoreResultRepository.save(loanScoreResult);
        loanScoreResultDto.setLoanScoreResult(loanScoreResult);
        loanScoreResultDto.setBaseUrl(ADMIN_LOANSCORE);
        return loanScoreResultDto;

    }
}
