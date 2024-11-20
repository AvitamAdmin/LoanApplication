package com.avitam.bankloanapplication.web.controllers.admin;

import com.avitam.bankloanapplication.model.dto.LoanScoreResultDto;
import com.avitam.bankloanapplication.model.entity.LoanScoreResult;
import com.avitam.bankloanapplication.repository.LoanScoreResultRepository;
import com.avitam.bankloanapplication.service.LoanScoreResultService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/LoanScoreResult")
public class LoanScoreResultController extends BaseController {
    @Autowired
    private LoanScoreResultService loanScoreResultService;
    @Autowired
    private LoanScoreResultRepository loanScoreRepository;

    private static final String ADMIN_LOANSCORE = "/admin/loanscore" ;

    @PostMapping
    public LoanScoreResultDto getAllLoanScore(LoanScoreResultDto loanScoreDto){
        Pageable pageable=getPageable(loanScoreDto.getPage(),loanScoreDto.getSizePerPage(),loanScoreDto.getSortDirection(),loanScoreDto.getSortField());
        LoanScoreResult loanScoreResult = loanScoreDto.getLoanScoreResult();
        Page<LoanScoreResult> page=isSearchActive(loanScoreResult) !=null ? loanScoreRepository.findAll(Example.of(loanScoreResult),pageable) : loanScoreRepository.findAll(pageable);
        loanScoreDto.setLoanScoreResultList(page.getContent());
        loanScoreDto.setBaseUrl(ADMIN_LOANSCORE);
        loanScoreDto.setTotalPages(page.getTotalPages());
        loanScoreDto.setTotalRecords(page.getTotalElements());
        return loanScoreDto;

    }
    @PostMapping("/get")
    public LoanScoreResultDto createLoanScore(LoanScoreResultDto request){
        return loanScoreResultService.createLoanScore(request);

    }
    @PostMapping("/update")
    public LoanScoreResultDto updateLoanScore(LoanScoreResultDto request){
        return loanScoreResultService.createLoanScore(request);
    }
    @PostMapping("/delete")
    public LoanScoreResultDto deleteLoanScore(LoanScoreResultDto request){
        for(String id:request.getRecordId().split(",")){
            loanScoreRepository.deleteByRecordId(id);
        }
        request.setMessage("Data deleted successfully");
        request.setBaseUrl(ADMIN_LOANSCORE);
        return request;
    }

    @GetMapping("/get")
    public LoanScoreResultDto getActiveLoanScore(LoanScoreResultDto request){
        LoanScoreResult loanScoreResult = loanScoreRepository.findByRecordId(request.getRecordId());
        request.setLoanScoreResult(loanScoreResult);
        request.setBaseUrl(ADMIN_LOANSCORE);
        return request;
    }

}
