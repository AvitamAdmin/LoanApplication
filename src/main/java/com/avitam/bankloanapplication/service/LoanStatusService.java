package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanStatusDto;
import com.avitam.bankloanapplication.model.dto.LoanStatusWsDto;
import com.avitam.bankloanapplication.model.entity.LoanStatus;
import com.avitam.bankloanapplication.repository.LoanStatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanStatusService {
    @Autowired
    private LoanStatusRepository loanStatusRepository;
    @Autowired
    private ModelMapper modelMapper;
    private static final String ADMIN_LOANSTATUS= "/admin/loanStatus";


    public LoanStatusWsDto handleEdit(LoanStatusWsDto request) {
        LoanStatusWsDto loanStatusWsDto = new LoanStatusWsDto();
        LoanStatus loanStatus=new LoanStatus();
        List<LoanStatusDto> loanStatusDtos = request.getLoanStatusDtos();
        List<LoanStatus> loanStatuses = new ArrayList<>();
        for(LoanStatusDto loanStatusDto: loanStatusDtos) {

            if (loanStatusDto.getRecordId() != null) {
                loanStatus = loanStatusRepository.findByRecordId(loanStatusDto.getRecordId());
                modelMapper.map(loanStatusDto, loanStatus);
                loanStatusRepository.save(loanStatus);
            } else {
                loanStatus = modelMapper.map(loanStatusDto, LoanStatus.class);
                loanStatusRepository.save(loanStatus);
            }
            if (request.getRecordId() == null) {
                loanStatus.setRecordId(String.valueOf(loanStatus.getId().getTimestamp()));
            }
            loanStatusRepository.save(loanStatus);
            loanStatuses.add(loanStatus);
            request.setBaseUrl(ADMIN_LOANSTATUS);
        }
        request.setLoanStatusDtos(modelMapper.map(loanStatuses,List.class));
        return request;
    }
}
