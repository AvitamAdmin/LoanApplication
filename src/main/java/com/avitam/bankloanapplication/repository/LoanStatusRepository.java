package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.LoanStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LoanStatusRepository extends MongoRepository<LoanStatus,String> {

    LoanStatus findByRecordId(String recordId);

    void deleteByRecordId(String RecordId);

    List<LoanStatus> findByStatusOrderByIdentifier(boolean b);

    LoanStatus findByName(String inactive);
}
