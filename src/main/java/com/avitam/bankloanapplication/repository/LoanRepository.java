package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanRepository extends MongoRepository<Loan,Long> {


}
