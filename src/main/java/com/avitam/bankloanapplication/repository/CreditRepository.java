package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.Credit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditRepository extends MongoRepository<Credit,String> {
    Object findByStatusOrderByIdentifier(boolean b);

    Credit findByRecordId(String recordId);

    void deleteByRecordId(String recordId);
}
