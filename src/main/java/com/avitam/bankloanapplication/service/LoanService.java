package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.exception.LoanNotFoundException;
import com.avitam.bankloanapplication.model.dto.LoanDto;
import com.avitam.bankloanapplication.repository.CustomerRepository;
import com.avitam.bankloanapplication.repository.LoanRepository;

import com.avitam.bankloanapplication.model.entity.Loan;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class LoanService {


    @Autowired
    private  LoanRepository loanRepository;

    @Autowired
    private  CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;


    public static final String ADMIN_lOAN = "/admin/loan";


    public LoanDto createLoan(LoanDto request) {
        LoanDto loanDto = new LoanDto();
        Loan loan = null;
        if(request.getRecordId()!=null){
            Loan requestData = request.getLoan();
            loan= loanRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, loan);
            loanRepository.save(loan);
        }
        else {
            loan = request.getLoan();
            loanRepository.save(loan);
        }
        if(request.getRecordId()==null){
            loan.setRecordId(String.valueOf(loan.getId().getTimestamp()));
        }
        loanRepository.save(loan);
        loanDto.setLoan(loan);
        loanDto.setBaseUrl(ADMIN_lOAN);
        return loanDto;

    }





//    //TODO: known issue:     "message": "Can not set java.util.Date field com.gulbalasalamov.bankloanapplication.model.entity.Loan.loanDate to java.lang.String",
//    public void updateLoanPartially(Long loanId, Map<Object, Object> objectMap) {
//        var loanById = findLoanById(loanId);
//        loanById.ifPresent(loan -> objectMap.forEach((key, value) -> {
//
//            Field field = ReflectionUtils.findField(Loan.class, (String) key);
//            field.setAccessible(true);
//            ReflectionUtils.setField(field, loan, value);
//            loanRepository.save(loan);
//        }));
//    }

//    public void updateLoan(Long loanId, Loan loan) {
//        var loanById = findLoanById(loanId);
//        loanById.ifPresent(updatedLoan -> {
//                    updatedLoan.setId(loan.getId());
//                    updatedLoan.setLoanType(loan.getLoanType());
//                    updatedLoan.setLoanLimit(loan.getLoanLimit());
//                    updatedLoan.setLoanDate(loan.getLoanDate());
//                    updatedLoan.setLoanStatus(loan.getLoanStatus());
//                    updatedLoan.setLoanScoreResult(loan.getLoanScoreResult());
//                    updatedLoan.setLoanApplication(loan.getLoanApplication());
//                    loanRepository.save(updatedLoan);
//                }
//        );
    }






