package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    // JPQL
    boolean existsByUsername(String username);

    User findByUsername(String username);

    void deleteByUsername(String username);

    User findByEmail(String username);

    User findByMobileNumber(String username);

    User findByResetPasswordToken(String token);

    User findByRecordId(String recordId);

}