package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.Loan;
import com.avitam.bankloanapplication.model.entity.LoanDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {


    Loan findByRecordId(String recordId);

    void deleteByRecordId(String RecordId);

    List<Loan> findByStatus(boolean b);

    List<Loan> findByLoanTypeAndStatus(String loanType, boolean status);

    List<Loan> findByRecordIdAndStatus(String recordId, boolean status);

    LoanDetails findByLoanDetailsId(String loanDetailsId);
}
