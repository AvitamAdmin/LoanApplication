package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("UserRepository")
public interface UserRepository extends MongoRepository<User, Integer> {

    // JPQL
    boolean existsByUsername(String username);

    User findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

    User findByEmail(String username);

    User findByMobileNumber(String username);

    User findByResetPasswordToken(String token);
}