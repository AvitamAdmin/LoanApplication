package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.LoanType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTypeRepository extends MongoRepository<LoanType,String> {
    LoanType findByRecordId(String recordId);

    LoanType findByName(String name);

    void deleteByRecordId(String id);

}