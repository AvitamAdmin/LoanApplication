package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("RoleRepository")
public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    Optional<Role> findById(String id);

    Role findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Role> findByStatusOrderByIdentifier(boolean b);
}
