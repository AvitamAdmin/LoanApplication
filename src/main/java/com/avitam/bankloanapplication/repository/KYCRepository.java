package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.KYC;


import org.springframework.data.mongodb.repository.MongoRepository;



public interface KYCRepository  extends MongoRepository<KYC,String> {

    Object findByStatusOrderByIdentifier(boolean b);

    KYC findByRecordId(String recordId);

    void deleteByRecordId(String recordId);
}
