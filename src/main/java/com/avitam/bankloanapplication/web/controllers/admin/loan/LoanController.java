package com.avitam.bankloanapplication.web.controllers.admin.loan;

import com.avitam.bankloanapplication.model.entity.Loan;
import com.avitam.bankloanapplication.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/loan")
public class LoanController {

    final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/create")
    public ResponseEntity<Integer> createLoan(@RequestBody Loan loan) {
        loanService.createLoan(loan);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("get/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long loanId) {
        return new ResponseEntity<>(loanService.getLoanById(loanId), HttpStatus.OK);
    }

    @PutMapping("/update/{loanId}")
    public ResponseEntity<Integer> updateLoan(@PathVariable Long loanId, @RequestBody Loan loan) {
        loanService.updateLoan(loanId, loan);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO: need enum-string fix in LoanService
    @PatchMapping("/update/{loanId}")
    public ResponseEntity<Integer> updateLoanPartially(@PathVariable Long loanId, @RequestBody Map<Object, Object> objectMap) {
        loanService.updateLoanPartially(loanId, objectMap);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{loanId}")
    public ResponseEntity<Integer> deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
