package com.avitam.bankloanapplication.web.controllers.admin.customer;

import com.avitam.bankloanapplication.core.service.CoreService;
import com.avitam.bankloanapplication.model.dto.*;
import com.avitam.bankloanapplication.model.entity.Customer;
import com.avitam.bankloanapplication.repository.CustomerRepository;
import com.avitam.bankloanapplication.service.impl.CustomerServiceImpl;
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
@RequestMapping("/admin/customer")
public class CustomerController extends BaseController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private CustomerServiceImpl customerService;

    public static final String ADMIN_CUSTOMER = "/admin/customer";

    @PostMapping
    public CustomerWsDto getAllCustomers(@RequestBody CustomerWsDto customerWsDto){

        Pageable pageable=getPageable(customerWsDto.getPage(),customerWsDto.getSizePerPage(),customerWsDto.getSortDirection(),customerWsDto.getSortField());
        CustomerDto customerDto = CollectionUtils.isNotEmpty(customerWsDto.getCustomerDtoList()) ? customerWsDto.getCustomerDtoList().get(0) : new CustomerDto();
        Customer customer = modelMapper.map(customerDto, Customer.class);
        Page<Customer> page=isSearchActive(customer) !=null ? customerRepository.findAll(Example.of(customer), pageable) : customerRepository.findAll(pageable);
        customerWsDto.setCustomerDtoList(modelMapper.map(page.getContent(), List.class));
        customerWsDto.setTotalPages(page.getTotalPages());
        customerWsDto.setTotalRecords(page.getTotalElements());
        customerWsDto.setBaseUrl(ADMIN_CUSTOMER);
        return customerWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public CustomerWsDto getActiveCustomerList(@RequestBody CustomerWsDto request) {
        CustomerWsDto customerWsDto = new CustomerWsDto();
        List<Customer> customers = new ArrayList<>();
        for(CustomerDto customerDto: request.getCustomerDtoList()) {
            customers.add(customerRepository.findByRecordId(customerDto.getRecordId()));
        }
        customerWsDto.setCustomerDtoList(modelMapper.map(customers,List.class));
        customerWsDto.setBaseUrl(ADMIN_CUSTOMER);
        return customerWsDto;
    }

//    @PostMapping("/getedit")
//    @ResponseBody
//    public CustomerWsDto editCustomer(@RequestBody CustomerWsDto request) {
//        CustomerWsDto customerDto = new CustomerDto();
//        Customer customer = customerRepository.findByRecordId(request.getRecordId());
//        customerDto=modelMapper.map(customer, CustomerDto.class);
//        customerDto.setBaseUrl(ADMIN_CUSTOMER);
//        return customerDto;
//    }

    @PostMapping("/edit")
    @ResponseBody
    public CustomerWsDto handleEdit(@RequestBody CustomerWsDto request) {
        return customerService.handleEdit(request);
    }

//    @GetMapping("/add")
//    @ResponseBody
//    public CustomerDto addCustomer() {
//        CustomerDto customerDto = new CustomerDto();
//        //customerDto.setCustomerList(customerRepository.findByStatusOrderByIdentifier(true));
//        customerDto.setBaseUrl(ADMIN_CUSTOMER);
//        return customerDto;
//    }

    @PostMapping("/delete")
    @ResponseBody
    public CustomerWsDto deleteCustomer(@RequestBody CustomerWsDto customerWsDto) {
        for (CustomerDto customerDto : customerWsDto.getCustomerDtoList()) {
            customerRepository.deleteByRecordId(customerDto.getRecordId());
        }
        customerWsDto.setMessage("Data deleted successfully");
        customerWsDto.setBaseUrl(ADMIN_CUSTOMER);
        return customerWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody
    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new Customer());
    }

}
