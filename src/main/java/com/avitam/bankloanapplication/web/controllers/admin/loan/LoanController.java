package com.avitam.bankloanapplication.web.controllers.admin.loan;

import com.avitam.bankloanapplication.model.dto.LoanDto;
import com.avitam.bankloanapplication.model.dto.LoanWsDto;
import com.avitam.bankloanapplication.model.dto.SearchDto;
import com.avitam.bankloanapplication.model.entity.Loan;
import com.avitam.bankloanapplication.repository.LoanRepository;
import com.avitam.bankloanapplication.service.LoanService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/loans/loan")
public class LoanController extends BaseController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ModelMapper modelMapper;

    private static final String ADMIN_LOAN = "/loans/loan";

    @PostMapping
    public LoanWsDto getAllLoan(@RequestBody LoanWsDto loanWsDto) {
        Pageable pageable = getPageable(loanWsDto.getPage(), loanWsDto.getSizePerPage(), loanWsDto.getSortDirection(), loanWsDto.getSortField());
        LoanDto loanDto = CollectionUtils.isNotEmpty(loanWsDto.getLoanDtoList()) ? loanWsDto.getLoanDtoList().get(0) : new LoanDto();
        Loan loan = modelMapper.map(loanDto, Loan.class);
        Page<Loan> page = isSearchActive(loan) != null ? loanRepository.findAll(Example.of(loan), pageable) : loanRepository.findAll(pageable);
        loanWsDto.setLoanDtoList(modelMapper.map(page.getContent(), List.class));
        loanWsDto.setBaseUrl(ADMIN_LOAN);
        loanWsDto.setTotalPages(page.getTotalPages());
        loanWsDto.setTotalRecords(page.getTotalElements());
        return loanWsDto;
    }

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
        loanWsDto.setLoanDtoList(modelMapper.map(loans, List.class));
        loanWsDto.setBaseUrl(ADMIN_LOAN);
        return loanWsDto;
    }

    @GetMapping("/getByLoanType")
    public LoanWsDto getByLoanType(@RequestParam String loanType) {
        LoanWsDto loanWsDto = new LoanWsDto();
        List<Loan> loans = loanRepository.findByLoanTypeAndStatus(loanType, true);
        loanWsDto.setLoanDtoList(modelMapper.map(loans, List.class));
        loanWsDto.setBaseUrl(ADMIN_LOAN);
        return loanWsDto;
    }

    @PostMapping("/getByLoanRecordId")
    public LoanWsDto getByRecordId(@RequestBody String recordId) {
        LoanWsDto loanWsDto = new LoanWsDto();
        List<Loan> loans = loanRepository.findByRecordIdAndStatus(recordId,true);
        loanWsDto.setLoanDtoList(modelMapper.map(loans, List.class));
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

}
