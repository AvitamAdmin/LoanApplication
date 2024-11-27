package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanScoreResultDto;
import com.avitam.bankloanapplication.model.dto.LoanScoreResultWsDto;
import com.avitam.bankloanapplication.model.entity.LoanScoreResult;
import com.avitam.bankloanapplication.repository.LoanScoreResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class LoanScoreResultService {
    private static final String ADMIN_LOANSCORE = "/admin/loanScoreResult";
    @Autowired
    private LoanScoreResultRepository loanScoreResultRepository;

    @Autowired
    private ModelMapper modelMapper;

    public LoanScoreResultWsDto createLoanScore(LoanScoreResultWsDto request) {
        LoanScoreResult loanScoreResult = null;
        List<LoanScoreResultDto> loanScoreDtos=request.getLoanScoreDtos();
        List<LoanScoreResult> loanScoreResults=new ArrayList<>();
        for(LoanScoreResultDto loanScoreDto:loanScoreDtos){
        if(loanScoreDto.getRecordId()!=null){
            loanScoreResult= loanScoreResultRepository.findByRecordId(loanScoreDto.getRecordId());
            modelMapper.map(loanScoreDto, loanScoreResult);
            loanScoreResult.setLastModified(new Date());
            loanScoreResultRepository.save(loanScoreResult);
        }
        else {
            loanScoreResult = modelMapper.map(loanScoreDto, LoanScoreResult.class);
            loanScoreResult.setCreationTime(new Date());
            loanScoreResult.setStatus(true);
            loanScoreResultRepository.save(loanScoreResult);
        }
        if(request.getRecordId()==null){
            loanScoreResult.setRecordId(String.valueOf(loanScoreResult.getId().getTimestamp()));
        }
        loanScoreResultRepository.save(loanScoreResult);
        loanScoreResults.add(loanScoreResult);
        request.setBaseUrl(ADMIN_LOANSCORE);
        }
        request.setLoanScoreDtos(modelMapper.map(loanScoreResults,List.class));
        return request;

    }
}
