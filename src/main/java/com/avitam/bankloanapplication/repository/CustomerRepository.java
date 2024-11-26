package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByNationalIdentityNumber(String nationalIdentityNumber);

    Customer findByRecordId(String recordId);

    List<Customer> findByStatusOrderByIdentifier(boolean b);

    void deleteByRecordId(String id);

    Customer findByEmail(String inputString);

    Customer findByPhone(String inputString);


}
