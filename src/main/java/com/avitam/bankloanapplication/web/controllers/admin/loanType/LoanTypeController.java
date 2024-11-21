package com.avitam.bankloanapplication.web.controllers.admin.loanType;

import com.avitam.bankloanapplication.model.dto.LoanTypeDto;
import com.avitam.bankloanapplication.model.dto.NotificationDto;
import com.avitam.bankloanapplication.model.dto.RoleDto;
import com.avitam.bankloanapplication.model.entity.LoanType;
import com.avitam.bankloanapplication.model.entity.Role;
import com.avitam.bankloanapplication.repository.LoanTypeRepository;
import com.avitam.bankloanapplication.service.LoanTypeService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/loanType")
public class LoanTypeController extends BaseController {

    private static final String ADMIN_LOANTYPE = "/admin/loanType";
    @Autowired
    private LoanTypeRepository loanTypeRepository;
    @Autowired
    private LoanTypeService loanTypeService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public LoanTypeDto getAllLoanTypes(@RequestBody LoanTypeDto loanTypeDto){
        Pageable pageable=getPageable(loanTypeDto.getPage(),loanTypeDto.getSizePerPage(),loanTypeDto.getSortDirection(),loanTypeDto.getSortField());
        LoanType loanType = modelMapper.map(loanTypeDto, LoanType.class);
        Page<LoanType> page=isSearchActive(loanType) !=null ? loanTypeRepository.findAll(Example.of(loanType),pageable) : loanTypeRepository.findAll(pageable);
        loanTypeDto.setLoanTypeList(page.getContent().stream().map(loanType1 -> modelMapper.map(loanType, String.class)).collect(Collectors.toList()));
        loanTypeDto.setBaseUrl(ADMIN_LOANTYPE);
        loanTypeDto.setTotalPages(page.getTotalPages());
        loanTypeDto.setTotalRecords(page.getTotalElements());
        return loanTypeDto;

    }
    @GetMapping("/get")
    public LoanTypeDto getLoanType(@RequestBody LoanTypeDto request) {
        LoanType loanType=loanTypeRepository.findByRecordId(request.getRecordId());
        request=modelMapper.map(loanType, LoanTypeDto.class);
        request.setBaseUrl(ADMIN_LOANTYPE);
        return request;
    }

    @PostMapping("/edit")
    public LoanTypeDto handleEdit(@RequestBody LoanTypeDto request) {

        return loanTypeService.handleEdit(request);
    }

    @PostMapping("/delete")
    public LoanTypeDto delete(@RequestBody LoanTypeDto loanTypeDto) {
        for (String id : loanTypeDto.getRecordId().split(",")) {
            loanTypeRepository.deleteByRecordId(id);
        }
        loanTypeDto.setMessage("Data deleted successfully");
        loanTypeDto.setBaseUrl(ADMIN_LOANTYPE);
        return loanTypeDto;
    }
}