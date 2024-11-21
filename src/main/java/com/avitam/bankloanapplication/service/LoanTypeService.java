package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanTypeDto;
import com.avitam.bankloanapplication.model.entity.Loan;
import com.avitam.bankloanapplication.model.entity.LoanType;
import com.avitam.bankloanapplication.repository.LoanTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanTypeService {

    private static final String ADMIN_LOANTYPE = "/admin/loanType";
    @Autowired
    private LoanTypeRepository loanTypeRepository;
    @Autowired
    private ModelMapper modelMapper;


    public LoanTypeDto handleEdit(LoanTypeDto request) {
        LoanType loanType=new LoanType();
        if(request.getRecordId()!=null){
            loanType= loanTypeRepository.findByRecordId(request.getRecordId());
            modelMapper.map(request, loanType);
            loanTypeRepository.save(loanType);
        }
        else {
            loanType = modelMapper.map(request, LoanType.class);
            loanTypeRepository.save(loanType);
        }
        if(request.getRecordId()==null){
            loanType.setRecordId(String.valueOf(loanType.getId().getTimestamp()));
        }
        loanTypeRepository.save(loanType);
        request=modelMapper.map(loanType, LoanTypeDto.class);
        request.setBaseUrl(ADMIN_LOANTYPE);
        return request;
    }
}
