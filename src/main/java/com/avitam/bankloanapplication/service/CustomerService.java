package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.core.service.CoreService;
import com.avitam.bankloanapplication.model.dto.CustomerDto;
import com.avitam.bankloanapplication.model.dto.CustomerWsDto;
import com.avitam.bankloanapplication.repository.CustomerRepository;
import com.avitam.bankloanapplication.model.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
//    @Autowired
//    private BaseService baseService;
    @Autowired
    private CustomerRepository customerRepository;

    public static final String ADMIN_CUSTOMER = "/admin/customer";

    public Customer findByRecordId(String recordId) {
        return customerRepository.findByRecordId(recordId);
    }

    public void deleteByRecordId(String recordId) {
        customerRepository.deleteByRecordId(recordId);
    }

    public CustomerWsDto handleEdit(CustomerWsDto request) {
        CustomerWsDto customerWsDto = new CustomerWsDto();
        Customer customer=new Customer();
        List<CustomerDto> customerDtos = request.getCustomerDtos();
        List<Customer> customers = new ArrayList<>();
        for(CustomerDto customerDto: customerDtos) {

            if (customerDto.getRecordId() != null) {
                customer = customerRepository.findByRecordId(customerDto.getRecordId());
                modelMapper.map(customerDto, customer);
                customerRepository.save(customer);
            } else {
                customer = modelMapper.map(customerDto, Customer.class);
                customer.setCreationTime(new Date());
                customer.setStatus(true);
                customerRepository.save(customer);
            }
            if (request.getRecordId() == null) {
                customer.setRecordId(String.valueOf(customer.getId().getTimestamp()));
            }
            customerRepository.save(customer);
            customers.add(customer);
            request.setBaseUrl(ADMIN_CUSTOMER);
        }
        request.setCustomerDtos(modelMapper.map(customers,List.class));
        return request;
    }

    public void updateByRecordId(String recordId) {

        Customer  customerOptional=customerRepository.findByRecordId(recordId);
        if(customerOptional!=null)
        {
            customerRepository.save(customerOptional);
        }

    }
}
