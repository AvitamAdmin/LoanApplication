package com.avitam.bankloanapplication.web.controllers.admin.loanapplication;

import com.avitam.bankloanapplication.core.service.CoreService;
import com.avitam.bankloanapplication.model.dto.*;
import com.avitam.bankloanapplication.model.entity.LoanApplication;
import com.avitam.bankloanapplication.repository.CustomerRepository;
import com.avitam.bankloanapplication.repository.LoanApplicationRepository;
import com.avitam.bankloanapplication.repository.LoanRepository;
import com.avitam.bankloanapplication.service.LoanApplicationService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/loanApplication")
public class LoanApplicationController extends BaseController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanApplicationService loanApplicationService;

    public static final String ADMIN_LOANAPPLICATION = "/admin/loanApplication";

    @PostMapping
    @ResponseBody
    public LoanApplicationWsDto getAllLoanApplications(@RequestBody LoanApplicationWsDto loanApplicationWsDto){
        Pageable pageable=getPageable(loanApplicationWsDto.getPage(),loanApplicationWsDto.getSizePerPage(),loanApplicationWsDto.getSortDirection(),loanApplicationWsDto.getSortField());
        LoanApplicationDto loanApplicationDto = CollectionUtils.isNotEmpty(loanApplicationWsDto.getLoanApplicationDtos()) ? loanApplicationWsDto.getLoanApplicationDtos().get(0) : new LoanApplicationDto();
        LoanApplication loanApplication = modelMapper.map(loanApplicationWsDto, LoanApplication.class);
        Page<LoanApplication> page=isSearchActive(loanApplication) !=null ? loanApplicationRepository.findAll(Example.of(loanApplication),pageable) : loanApplicationRepository.findAll(pageable);
        loanApplicationWsDto.setLoanApplicationDtos(modelMapper.map(page.getContent(), List.class));
        loanApplicationWsDto.setBaseUrl(ADMIN_LOANAPPLICATION);
        loanApplicationWsDto.setTotalPages(page.getTotalPages());
        loanApplicationWsDto.setTotalRecords(page.getTotalElements());
        return loanApplicationWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public LoanApplicationWsDto getLoanType(@RequestBody LoanApplicationWsDto request) {
        LoanApplicationWsDto loanApplicationWsDto = new LoanApplicationWsDto();
        List<LoanApplication> loanApplications = new ArrayList<>();
        for(LoanApplicationDto loanApplicationDto: request.getLoanApplicationDtos()) {
            loanApplications.add(loanApplicationRepository.findByRecordId(loanApplicationDto.getRecordId()));
        }
        loanApplicationWsDto.setLoanApplicationDtos(modelMapper.map(loanApplications,List.class));
        loanApplicationWsDto.setBaseUrl(ADMIN_LOANAPPLICATION);
        return loanApplicationWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public LoanApplicationWsDto editLoanApplication(@RequestBody LoanApplicationWsDto request) {
        LoanApplicationWsDto loanApplicationWsDto = new LoanApplicationWsDto();
        List<LoanApplication> loanApplications=new ArrayList<>();
        for(LoanApplicationDto loanApplicationDto:request.getLoanApplicationDtos()) {
             loanApplications.add(loanApplicationRepository.findByRecordId(loanApplicationDto.getRecordId()));
        }loanApplicationWsDto.setLoanApplicationDtos(modelMapper.map(loanApplications,List.class));
        loanApplicationWsDto.setBaseUrl(ADMIN_LOANAPPLICATION);
        return loanApplicationWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public LoanApplicationWsDto handleEdit(@RequestBody LoanApplicationWsDto request) {
        return loanApplicationService.handleEdit(request);
    }

    @GetMapping("/getLoan")
    @ResponseBody
    public LoanDto getLoanApplicationResult(@RequestBody LoanApplicationDto request) {

        return loanApplicationService.getLoanApplicationResult(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public LoanApplicationWsDto addLoanApplication() {
        LoanApplicationWsDto loanApplicationWsDto = new LoanApplicationWsDto();
        List<LoanApplicationDto> loanApplicationDtoList=new ArrayList<>();
        loanApplicationDtoList.add(loanApplicationRepository.findByStatusOrderByIdentifier(true));
        loanApplicationWsDto.setLoanApplicationDtos(loanApplicationDtoList);
        loanApplicationWsDto.setBaseUrl(ADMIN_LOANAPPLICATION);
        return loanApplicationWsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public LoanApplicationWsDto deleteLoanApplication(@RequestBody LoanApplicationWsDto loanApplicationWsDto) {

        for (LoanApplicationDto loanApplicationDto1 : loanApplicationWsDto.getLoanApplicationDtos()) {
            loanApplicationRepository.deleteByRecordId(loanApplicationDto1.getRecordId());
        }
        loanApplicationWsDto.setMessage("Data deleted successfully");
        loanApplicationWsDto.setBaseUrl(ADMIN_LOANAPPLICATION);
        return loanApplicationWsDto;
    }
}
