package com.avitam.bankloanapplication.model.mapper;

import com.avitam.bankloanapplication.model.dto.CustomerDto;
import com.avitam.bankloanapplication.model.entity.Customer;

public class Mapper {
    public static CustomerDto toDto(Customer customer) {
        CustomerDto customerDTO = new CustomerDto();
        //customerDTO.setId(customer.getId());
//        customerDTO.setNationalIdentityNumber(customer.getNationalIdentityNumber());
//        customerDTO.setFirstName(customer.getFirstName());
//        customerDTO.setLastName(customer.getLastName());
//        customerDTO.setPhone(customer.getPhone());
//        customerDTO.setEmail(customer.getEmail());
//        customerDTO.setMonthlyIncome(customer.getMonthlyIncome());
//        customerDTO.setGender(customer.getGender());
//        customerDTO.setAge(customer.getAge());
//        customerDTO.setLoanScore(customer.getLoanScore());
//        customerDTO.setLoanApplications(customer.getLoanApplications());
        return customerDTO;
    }

    public static Customer toEntity(CustomerDto customerDTO) {
        Customer customer = new Customer();
        //customer.setId(customerDTO.getId());
//        customer.setNationalIdentityNumber(customerDTO.getNationalIdentityNumber());
////        customer.setNationalIdentityNumber(customerDTO.getNationalIdentityNumber());
//        customer.setFirstName(customerDTO.getFirstName());
//        customer.setLastName(customerDTO.getLastName());
//        customer.setPhone(customerDTO.getPhone());
//        customer.setEmail(customerDTO.getEmail());
//        customer.setMonthlyIncome(customerDTO.getMonthlyIncome());
//        customer.setGender(customerDTO.getGender());
//        customer.setAge(customerDTO.getAge());
//        customer.setLoanScore(customerDTO.getLoanScore());
//        customer.setLoanApplications(customerDTO.getLoanApplications());
        return customer;
    }

}
