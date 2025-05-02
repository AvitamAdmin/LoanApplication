package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.CustomerDto;
import com.avitam.bankloanapplication.model.dto.CustomerWsDto;
import com.avitam.bankloanapplication.model.entity.Customer;

public interface CustomerService {

    Customer findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    CustomerWsDto handleEdit(CustomerDto request);

    void updateByRecordId(String recordId);

    Object getCustomerByNationalIdentityNumber(Object o);

    Object getAllCustomers();

    boolean updateResetPasswordToken(String token, String email);

    boolean updateOtp(String token, String email);

    Customer getByResetPasswordToken(String resetPasswordToken);

    void updatePassword(Customer customer, String password);
}
