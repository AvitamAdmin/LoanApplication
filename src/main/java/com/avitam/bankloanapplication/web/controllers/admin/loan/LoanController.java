package com.avitam.bankloanapplication.web.controllers.admin.loan;

import com.avitam.bankloanapplication.model.dto.*;
import com.avitam.bankloanapplication.model.entity.Loan;
import com.avitam.bankloanapplication.model.entity.LoanLimit;
import com.avitam.bankloanapplication.model.entity.LoanType;
import com.avitam.bankloanapplication.repository.LoanLimitRepository;
import com.avitam.bankloanapplication.repository.LoanRepository;
import com.avitam.bankloanapplication.service.LoanDetailsService;
import com.avitam.bankloanapplication.service.LoanService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loans/loan")
public class LoanController extends BaseController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LoanLimitRepository loanLimitRepository;


    private static final String ADMIN_LOAN = "/loans/loan";

    @PostMapping
    public LoanWsDto getAllLoan(@RequestBody LoanWsDto loanWsDto) {
        Pageable pageable = getPageable(loanWsDto.getPage(), loanWsDto.getSizePerPage(), loanWsDto.getSortDirection(), loanWsDto.getSortField());
        LoanDto loanDto = CollectionUtils.isNotEmpty(loanWsDto.getLoanDtoList()) ? loanWsDto.getLoanDtoList().get(0) : new LoanDto();
        Loan loan = modelMapper.map(loanDto, Loan.class);
        Page<Loan> page = isSearchActive(loan) != null ? loanRepository.findAll(Example.of(loan), pageable) : loanRepository.findAll(pageable);
        Type listType = new TypeToken<List<LoanDto>>() {}.getType();
        loanWsDto.setLoanDtoList(modelMapper.map(page.getContent(), listType));
        loanWsDto.setBaseUrl(ADMIN_LOAN);
        loanWsDto.setTotalPages(page.getTotalPages());
        loanWsDto.setTotalRecords(page.getTotalElements());
        return loanWsDto;
    }

    @PostMapping("/getEligibleLoans")
    public List<LoanDto> getEligibleLoans(@RequestBody LoanDto loanDto) {
        List<LoanDto> loanDtoList = new ArrayList<>();
        LoanLimit loanLimit = loanLimitRepository.findByCustomerId(loanDto.getCustomerId());
        if(loanLimit!=null){
            List<Loan> loans = loanRepository.findByLoanTypeAndStatus(loanDto.getLoanType(), true);
            Double loanLimitAmt = loanLimit.getLoanLimitAmount();
            Type listType = new TypeToken<List<LoanDto>>() {}.getType();
            loanDtoList.addAll(modelMapper.map(loans.stream().filter(loan -> loanLimitAmt >
                    loan.getDesiredLoan()).collect(Collectors.toList()), listType));
        }
        return loanDtoList;
    }



  /*  @GetMapping("/getEligibleLoans")
    public LoanWsDto getEligibleLoans(@RequestBody CustomerDto customerDto) {
        LoanWsDto loanWsDto = new LoanWsDto();
        List<LoanDto> loanDtoList = new ArrayList<>();

        LoanLimit loanLimit = loanLimitRepository.findByCustomerId(loanDto.getCustomerId());
        if(loanLimit!=null){
            List<Loan> loans = loanRepository.findAll();
            Double loanLimitAmt = loanLimit.getLoanLimitAmount();
            Type listType = new TypeToken<List<LoanDto>>() {}.getType();
            loanDtoList.addAll(modelMapper.map(loans.stream().filter(loan -> loanLimitAmt >
                    loan.getDesiredLoan()).collect(Collectors.toList()), listType));
        }

        loanWsDto.setLoanDtoList(loanDtoList);
        return loanWsDto;
    }*/

    @PostMapping("/edit")
    public LoanWsDto createLoan(@RequestBody LoanWsDto request) {
        return loanService.createLoan(request);

    }

    @PostMapping("/delete")
    public LoanWsDto deleteLoan(@RequestBody LoanWsDto request) {
        for (LoanDto loanDto : request.getLoanDtoList()) {
            loanRepository.deleteByRecordId(loanDto.getRecordId());
        }
        request.setMessage("Data deleted Successfully");
        request.setBaseUrl(ADMIN_LOAN);
        return request;

    }

    @GetMapping("/get")
    public LoanWsDto getLoanById() {
        LoanWsDto loanWsDto = new LoanWsDto();
        List<Loan> loans = loanRepository.findByStatus(true);
        Type listType = new TypeToken<List<LoanDto>>() {}.getType();
        loanWsDto.setLoanDtoList(modelMapper.map(loans, listType));
        loanWsDto.setBaseUrl(ADMIN_LOAN);
        return loanWsDto;
    }

    @GetMapping("/getByLoanType")
    public LoanWsDto getByLoanType(@RequestParam String loanType) {
        LoanWsDto loanWsDto = new LoanWsDto();
        List<Loan> loans = loanRepository.findByLoanTypeAndStatus(loanType, true);
        Type listType = new TypeToken<List<LoanDto>>() {}.getType();
        loanWsDto.setLoanDtoList(modelMapper.map(loans, listType));
        loanWsDto.setBaseUrl(ADMIN_LOAN);
        return loanWsDto;
    }

    @PostMapping("/getByLoanRecordId")
    public LoanWsDto getByRecordId(@RequestBody String recordId) {
        LoanWsDto loanWsDto = new LoanWsDto();
        List<Loan> loans = loanRepository.findByRecordIdAndStatus(recordId,true);
        Type listType = new TypeToken<List<LoanDto>>() {}.getType();
        loanWsDto.setLoanDtoList(modelMapper.map(loans, listType));
        loanWsDto.setBaseUrl(ADMIN_LOAN);
        return loanWsDto;
    }

//    //TODO: need enum-string fix in LoanService
//    @PatchMapping("/update/{loanId}")
//    public ResponseEntity<Integer> updateLoanPartially(@PathVariable Long loanId, @RequestBody Map<Object, Object> objectMap) {
//        loanService.updateLoanPartially(loanId, objectMap);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody
    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new Loan());
    }

    @PostMapping("/getEmiStatusTillDate")
    @ResponseBody
    public LoanDto getEmiStatusTillDate(@RequestBody LoanDto loanDto) {
        return loanService.getEmiStatusTillDate(loanDto);
    }




}
