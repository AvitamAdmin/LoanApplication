package com.avitam.bankloanapplication.web.controllers.admin.loanscoreresult;

import com.avitam.bankloanapplication.model.dto.LoanScoreResultDto;
import com.avitam.bankloanapplication.model.entity.LoanScoreResult;
import com.avitam.bankloanapplication.repository.LoanScoreResultRepository;
import com.avitam.bankloanapplication.service.LoanScoreResultService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/admin/loanScoreResult")
public class LoanScoreResultController extends BaseController {
    @Autowired
    private LoanScoreResultService loanScoreResultService;
    @Autowired
    private LoanScoreResultRepository loanScoreRepository;
    @Autowired
    private ModelMapper modelMapper;

    private static final String ADMIN_LOANSCORE = "/admin/loanScore" ;

    @PostMapping
    @ResponseBody
    public LoanScoreResultDto getAllLoanScore(@RequestBody LoanScoreResultDto loanScoreDto){
        Pageable pageable=getPageable(loanScoreDto.getPage(),loanScoreDto.getSizePerPage(),loanScoreDto.getSortDirection(),loanScoreDto.getSortField());
        LoanScoreResult loanScoreResult = modelMapper.map(loanScoreDto, LoanScoreResult.class);
        Page<LoanScoreResult> page=isSearchActive(loanScoreResult) !=null ? loanScoreRepository.findAll(Example.of(loanScoreResult),pageable) : loanScoreRepository.findAll(pageable);
        loanScoreDto.setLoanScoreResultList(Collections.singletonList(page.getContent().toString()));
        loanScoreDto.setBaseUrl(ADMIN_LOANSCORE);
        loanScoreDto.setTotalPages(page.getTotalPages());
        loanScoreDto.setTotalRecords(page.getTotalElements());
        return loanScoreDto;

    }
    @PostMapping("/edit")
    @ResponseBody
    public LoanScoreResultDto createLoanScore(@RequestBody LoanScoreResultDto request){
        return loanScoreResultService.createLoanScore(request);

    }

    @PostMapping("/delete")
    @ResponseBody
    public LoanScoreResultDto deleteLoanScore(@RequestBody LoanScoreResultDto request){
        for(String id:request.getRecordId().split(",")){
            loanScoreRepository.deleteByRecordId(id);
        }
        request.setMessage("Data deleted successfully");
        request.setBaseUrl(ADMIN_LOANSCORE);
        return request;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public LoanScoreResultDto getActiveLoanScore(@RequestBody LoanScoreResultDto request){
        LoanScoreResult loanScoreResult = loanScoreRepository.findByRecordId(request.getRecordId());
        request=modelMapper.map(loanScoreResult, LoanScoreResultDto.class);
        request.setBaseUrl(ADMIN_LOANSCORE);
        return request;
    }

}
