package com.avitam.bankloanapplication.service.impl;

import com.avitam.bankloanapplication.model.dto.LoanDto;
import com.avitam.bankloanapplication.model.dto.LoanWsDto;
import com.avitam.bankloanapplication.model.entity.LoanScoreResult;
import com.avitam.bankloanapplication.model.entity.LoanStatus;
import com.avitam.bankloanapplication.model.entity.LoanType;
import com.avitam.bankloanapplication.repository.*;

import com.avitam.bankloanapplication.model.entity.Loan;
import com.avitam.bankloanapplication.service.LoanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {


    @Autowired
    private  LoanRepository loanRepository;

    @Autowired
    private  CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LoanTypeRepository loanTypeRepository;

    @Autowired
    private LoanScoreResultRepository loanScoreResultRepository;

    @Autowired
    private LoanStatusRepository loanStatusRepository;

    public static final String ADMIN_lOAN = "/loans/loan";


    public LoanWsDto createLoan(LoanWsDto request) {
        Loan loan = new Loan();
        List<LoanDto> loanDtos=request.getLoanDtoList();
        List<LoanDto> loans=new ArrayList<>();
        for(LoanDto loanDto:loanDtos) {
            if (loanDto.getRecordId() != null) {
                loan = loanRepository.findByRecordId(request.getRecordId());
                modelMapper.map(loanDto, loan);
                loanRepository.save(loan);
                request.setMessage("Data updated successfully");
            } else {
                loan = modelMapper.map(loanDto, Loan.class);
                loan.setStatus(true);
                loan.setCreationTime(new Date());
                loanRepository.save(loan);
                LoanType loanType = loanTypeRepository.findByRecordId(loanDto.getLoanTypeId());
                String loanTypeName = loanType.getName();
                LoanStatus loanStatus = loanStatusRepository.findByRecordId(loanDto.getLoanStatusId());
                String loanStatusVar = loanStatus.getName();
                LoanScoreResult loanScoreResult = loanScoreResultRepository.findByRecordId(loanDto.getLoanScoreResultId());
                String loanResult=loanScoreResult.getName();
                loanDto.setLoanType(loanTypeName);
                loanDto.setLoanStatus(loanStatusVar);
                loanDto.setLoanScoreResult(loanResult);
            }
            if (request.getRecordId() == null) {
                loan.setRecordId(String.valueOf(loan.getId().getTimestamp()));
            }
            loanRepository.save(loan);
            loans.add(loanDto);
            loanDto.setBaseUrl(ADMIN_lOAN);

            request.setMessage("Data added Successfully");


        }
        request.setLoanDtoList(modelMapper.map(loans,List.class));

        return request;

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






