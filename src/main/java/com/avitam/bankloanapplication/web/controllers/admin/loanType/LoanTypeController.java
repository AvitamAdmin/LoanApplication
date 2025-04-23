package com.avitam.bankloanapplication.web.controllers.admin.loanType;

import com.avitam.bankloanapplication.model.dto.LoanTypeDto;
import com.avitam.bankloanapplication.model.dto.LoanTypeWsDto;
import com.avitam.bankloanapplication.model.dto.SearchDto;
import com.avitam.bankloanapplication.model.entity.LoanType;
import com.avitam.bankloanapplication.repository.LoanTypeRepository;
import com.avitam.bankloanapplication.service.LoanTypeService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/loans/loanType")
public class LoanTypeController extends BaseController {

    private static final String ADMIN_LOANTYPE = "/loans/loanType";
    @Autowired
    private LoanTypeRepository loanTypeRepository;
    @Autowired
    private LoanTypeService loanTypeService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public LoanTypeWsDto getAllLoanTypes(@RequestBody LoanTypeWsDto loanTypeWsDto) {
        Pageable pageable = getPageable(loanTypeWsDto.getPage(), loanTypeWsDto.getSizePerPage(), loanTypeWsDto.getSortDirection(), loanTypeWsDto.getSortField());
        LoanTypeDto loanTypeDto = CollectionUtils.isNotEmpty(loanTypeWsDto.getLoanTypeDtoList()) ? loanTypeWsDto.getLoanTypeDtoList().get(0) : new LoanTypeDto();
        LoanType loanType = modelMapper.map(loanTypeWsDto, LoanType.class);
        Page<LoanType> page = isSearchActive(loanType) != null ? loanTypeRepository.findAll(Example.of(loanType), pageable) : loanTypeRepository.findAll(pageable);
        loanTypeWsDto.setLoanTypeDtoList(modelMapper.map(page.getContent(), List.class));
        loanTypeWsDto.setBaseUrl(ADMIN_LOANTYPE);
        loanTypeWsDto.setTotalPages(page.getTotalPages());
        loanTypeWsDto.setTotalRecords(page.getTotalElements());
        return loanTypeWsDto;

    }

    @GetMapping("/get")
    public LoanTypeWsDto getLoanType() {
        LoanTypeWsDto loanTypewsDto = new LoanTypeWsDto();
        List<LoanType> loanTypes = loanTypeRepository.findByStatus(true);
        loanTypewsDto.setLoanTypeDtoList(modelMapper.map(loanTypes, List.class));
        loanTypewsDto.setBaseUrl(ADMIN_LOANTYPE);
        return loanTypewsDto;
    }



    @PostMapping("/edit")
    public LoanTypeWsDto handleEdit(@RequestBody LoanTypeWsDto request) {

        return loanTypeService.handleEdit(request);
    }

    @PostMapping("/delete")
    public LoanTypeWsDto delete(@RequestBody LoanTypeWsDto loanTypeWsDto) {
        for (LoanTypeDto loanTypeDto : loanTypeWsDto.getLoanTypeDtoList()) {
            loanTypeRepository.deleteByRecordId(loanTypeDto.getRecordId());
        }
        loanTypeWsDto.setMessage("Data deleted successfully");
        loanTypeWsDto.setBaseUrl(ADMIN_LOANTYPE);
        return loanTypeWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody
    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new LoanType());
    }
}