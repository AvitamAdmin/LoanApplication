package com.avitam.bankloanapplication.repository.generic;

import com.avitam.bankloanapplication.model.entity.BaseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericImportRepository<T extends BaseEntity> extends MongoRepository<T, ObjectId> {

    BaseEntity findByRecordId(String recordId);

    BaseEntity findByIdentifier(String identifier);
}
