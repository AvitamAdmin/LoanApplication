package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends MongoRepository<Loan,String> {


    Loan findByRecordId(String recordId);

    void deleteByRecordId(String RecordId);
}
