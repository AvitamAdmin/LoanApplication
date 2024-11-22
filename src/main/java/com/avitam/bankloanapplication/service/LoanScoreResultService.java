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
        LoanScoreResult requestData = null;
        List<LoanScoreResultDto> loanScoreDtos=request.getLoanScoreDtos();
        List<LoanScoreResult> loanScoreResults=new ArrayList<>();
        for(LoanScoreResultDto loanScoreDto:loanScoreDtos){
        if(loanScoreDto.getRecordId()!=null){
            requestData= loanScoreResultRepository.findByRecordId(loanScoreDto.getRecordId());
            modelMapper.map(loanScoreDto, requestData);
            requestData.setLastModified(new Date());
            loanScoreResultRepository.save(requestData);
        }
        else {
            requestData = modelMapper.map(loanScoreDto, LoanScoreResult.class);
            requestData.setCreationTime(new Date());
            loanScoreResultRepository.save(requestData);
        }
        if(request.getRecordId()==null){
            requestData.setRecordId(String.valueOf(requestData.getId().getTimestamp()));
        }
        loanScoreResultRepository.save(requestData);
        loanScoreResults.add(requestData);
        request.setBaseUrl(ADMIN_LOANSCORE);
        }
        request.setLoanScoreDtos(modelMapper.map(loanScoreResults,List.class));
        return request;

    }
}
