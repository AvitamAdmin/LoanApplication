package com.avitam.bankloanapplication.web.controllers.admin.loanlimit;

import com.avitam.bankloanapplication.model.dto.LoanLimitDto;
import com.avitam.bankloanapplication.model.dto.LoanScoreResultDto;
import com.avitam.bankloanapplication.model.entity.LoanLimit;
import com.avitam.bankloanapplication.model.entity.LoanScoreResult;
import com.avitam.bankloanapplication.repository.LoanLimitRepository;
import com.avitam.bankloanapplication.repository.LoanScoreResultRepository;
import com.avitam.bankloanapplication.service.LoanLimitService;
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
@RequestMapping("/admin/loanLimit")
public class LoanLimitController extends BaseController {
    @Autowired
    private LoanLimitService loanLimitService;
    @Autowired
    private LoanLimitRepository loanLimitRepository;
    @Autowired
    private ModelMapper modelMapper;

    private static final String ADMIN_LOANLIMIT = "/admin/loanLimit" ;

    @PostMapping
    @ResponseBody
    public LoanLimitDto getAllLoanLimits(@RequestBody LoanLimitDto loanLimitDto){
        Pageable pageable=getPageable(loanLimitDto.getPage(),loanLimitDto.getSizePerPage(),loanLimitDto.getSortDirection(),loanLimitDto.getSortField());
        LoanLimit loanLimit = modelMapper.map(loanLimitDto, LoanLimit.class);
        Page<LoanLimit> page=isSearchActive(loanLimit) !=null ? loanLimitRepository.findAll(Example.of(loanLimit),pageable) : loanLimitRepository.findAll(pageable);
        loanLimitDto.setLoanLimitList(Collections.singletonList(page.getContent().toString()));
        loanLimitDto.setBaseUrl(ADMIN_LOANLIMIT);
        loanLimitDto.setTotalPages(page.getTotalPages());
        loanLimitDto.setTotalRecords(page.getTotalElements());
        return loanLimitDto;

    }
    @PostMapping("/edit")
    @ResponseBody
    public LoanLimitDto createLoanScore(@RequestBody LoanLimitDto request){
        return loanLimitService.editLoanLimit(request);

    }

    @PostMapping("/delete")
    @ResponseBody
    public LoanLimitDto deleteLoanLimit(@RequestBody LoanLimitDto request){
        for(String id:request.getRecordId().split(",")){
            loanLimitRepository.deleteByRecordId(id);
        }
        request.setMessage("Data deleted successfully");
        request.setBaseUrl(ADMIN_LOANLIMIT);
        return request;
    }

    @PostMapping("/getEdit")
    @ResponseBody
    public LoanLimitDto getActiveLoanScore(@RequestBody LoanLimitDto request){
        LoanLimit loanLimit = loanLimitRepository.findByRecordId(request.getRecordId());
        request=modelMapper.map(loanLimit, LoanLimitDto.class);
        request.setBaseUrl(ADMIN_LOANLIMIT);
        return request;
    }
}
