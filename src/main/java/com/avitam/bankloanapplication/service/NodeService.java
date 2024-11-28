package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.NodeWsDto;
import com.avitam.bankloanapplication.model.entity.Node;

import java.util.List;

public interface NodeService {
    List<Node> getAllNodes();

    List<Node> getNodesForRoles();

    Node findByRecordId(String recordId) ;

    void deleteByRecordId(String recordId) ;

    NodeWsDto handleEdit(NodeWsDto request);

    void updateByRecordId(String recordId);
}
