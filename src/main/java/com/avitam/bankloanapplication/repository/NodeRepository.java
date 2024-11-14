package com.avitam.bankloanapplication.repository;

import com.avitam.bankloanapplication.model.entity.Node;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("NodeRepository")
public interface NodeRepository extends MongoRepository<Node, ObjectId> {


    List<Node> findByParentNodeId(String id);

    Optional<Node> findById(String id);

    Node findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    List<Node> findByStatusOrderByIdentifier(boolean b);
}
