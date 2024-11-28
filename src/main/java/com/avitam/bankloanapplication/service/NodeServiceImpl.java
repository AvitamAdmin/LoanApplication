package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.core.service.CoreService;
import com.avitam.bankloanapplication.model.dto.NodeDto;
import com.avitam.bankloanapplication.model.dto.NodeWsDto;
import com.avitam.bankloanapplication.model.entity.*;
import com.avitam.bankloanapplication.repository.NodeRepository;
import com.avitam.bankloanapplication.repository.UserRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NodeServiceImpl implements NodeService{

    private static final String ADMIN_INTERFACE = "/admin/interface";
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
//    @Autowired
//    private BaseService baseService;

    @Override
    public List<Node> getAllNodes() {
        List<Node> allNodes = nodeRepository.findByParentNodeId(null);
        if (CollectionUtils.isNotEmpty(allNodes)) {
            allNodes.sort(Comparator.comparing(nodes -> nodes.getDisplayPriority(),
                    Comparator.nullsFirst(Comparator.naturalOrder())));

            for (Node node : allNodes) {
                List<Node> nodes1 = nodeRepository.findByParentNodeId(String.valueOf(node.getId()));
//                nodes1.sort(Comparator.comparing(nodes -> nodes.getDisplayPriority(),
//                        Comparator.nullsFirst(Comparator.naturalOrder())));
                for (Node childNode : nodes1) {
                    childNode.setParentNode(node.getName()); // Set parent node for each child node
                }
                node.setChildNodes(nodes1);
            }
        }
        return allNodes;
    }

    @Override
    public List<Node> getNodesForRoles() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User currentUser = userRepository.findByEmail(principalObject.getUsername());
        List<Role> roles = currentUser.getRoles();
        Set<Node> nodes = new HashSet<>();
        for (Role role : roles) {
            nodes.addAll(role.getPermissions());
        }
        List<Node> allNodes = new ArrayList<>();
        List<Node> nodeList = nodes.stream().filter(node -> BooleanUtils.isNotFalse(node.getStatus())).collect(Collectors.toList());
        nodeList.sort(Comparator.comparing(node -> node.getDisplayPriority()));
        for (Node node : nodeList) {
            List<Node> nodes1 = nodeRepository.findByParentNodeId(String.valueOf(node.getId()));
            node.setChildNodes(nodes1);
            allNodes.add(node);
        }
        return allNodes;

    }

    @Override
    public Node findByRecordId(String recordId) {

        return nodeRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        nodeRepository.deleteByRecordId(recordId);
    }

    @Override
    public NodeWsDto handleEdit(NodeWsDto request) {
        Node node = new Node();
        List<NodeDto> nodeDtoListDtos=request.getNodeDtos();
        List<Node> nodes=new ArrayList<>();

        for(NodeDto nodeDto: nodeDtoListDtos) {

            if (nodeDto.getRecordId() != null) {
                node = nodeRepository.findByRecordId(nodeDto.getRecordId());
                modelMapper.map(nodeDto, node);
                nodeRepository.save(node);
            } else {
                node = modelMapper.map(nodeDto, Node.class);
                node.setStatus(true);
                node.setCreationTime(new Date());
                nodeRepository.save(node);
            }
            if (request.getRecordId() == null) {
                node.setRecordId(String.valueOf(node.getId().getTimestamp()));
            }
            nodeRepository.save(node);
            nodes.add(node);
            request.setBaseUrl(ADMIN_INTERFACE);
            }
        request.setNodeDtos(modelMapper.map(nodes,List.class));
        return request;
    }

    @Override
    public void updateByRecordId(String recordId) {
        Node node=nodeRepository.findByRecordId(recordId);
        if(node!=null) {

            nodeRepository.save(node);
        }
    }
}
