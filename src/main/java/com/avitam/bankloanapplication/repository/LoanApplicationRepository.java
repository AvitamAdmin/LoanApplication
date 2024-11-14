package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.LoanApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanApplicationRepository extends MongoRepository<LoanApplication,Long> {
}
