package com.avitam.bankloanapplication.web.controllers.admin.loan;

import com.avitam.bankloanapplication.model.dto.LoanDto;
import com.avitam.bankloanapplication.model.entity.Loan;
import com.avitam.bankloanapplication.repository.LoanRepository;
import com.avitam.bankloanapplication.service.LoanService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/loan")
public class LoanController extends BaseController {
    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanDto loanDto;

    @Autowired
    private LoanRepository loanRepository;

    private static final String ADMIN_LOAN= "/admin/loan";


    @PostMapping

    public LoanDto getAllLoan(@RequestBody LoanDto loanDto){
        Pageable pageable=getPageable(loanDto.getPage(),loanDto.getSizePerPage(),loanDto.getSortDirection(),loanDto.getSortField());
        Loan loan=loanDto.getLoan();
        Page<Loan> page=isSearchActive(loan)!=null ? loanRepository.findAll(Example.of(loan),pageable) : loanRepository.findAll(pageable);
        loanDto.setLoanList(page.getContent());
        loanDto.setBaseUrl(ADMIN_LOAN);
        loanDto.setTotalPages(page.getTotalPages());
        loanDto.setTotalRecords(page.getTotalElements());
        return loanDto;


    }

    @PostMapping("/edit")

    public LoanDto createLoan(@RequestBody LoanDto request) {
        return loanService.createLoan(request);

    }
    @PostMapping("/update/{loanId}")

    public LoanDto updateLoan(@RequestBody LoanDto request) {
        return loanService.createLoan(request);
    }

    @PostMapping("/delete")

    public LoanDto deleteLoan(@RequestBody LoanDto request){
        for (String id :loanDto.getRecordId().split(",")){
            loanRepository.deleteByRecordId(id);
        }
        loanDto.setMessage("Data deleted Successfully");
        loanDto.setBaseUrl(ADMIN_LOAN);
        return loanDto;

    }

    @GetMapping("/get")
    public LoanDto getLoanById(@RequestBody LoanDto request) {
        LoanDto loanDto1 = new LoanDto();
        Loan loan = loanRepository.findByRecordId(request.getRecordId());
        loanDto1.setLoan(loan);
        loanDto1.setBaseUrl(ADMIN_LOAN);
        return loanDto1;
    }






//    //TODO: need enum-string fix in LoanService
//    @PatchMapping("/update/{loanId}")
//    public ResponseEntity<Integer> updateLoanPartially(@PathVariable Long loanId, @RequestBody Map<Object, Object> objectMap) {
//        loanService.updateLoanPartially(loanId, objectMap);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}
