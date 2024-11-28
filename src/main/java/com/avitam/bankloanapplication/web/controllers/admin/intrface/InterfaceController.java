package com.avitam.bankloanapplication.web.controllers.admin.intrface;

import com.avitam.bankloanapplication.model.dto.NodeDto;
import com.avitam.bankloanapplication.model.dto.NodeWsDto;
import com.avitam.bankloanapplication.model.entity.Node;
import com.avitam.bankloanapplication.repository.NodeRepository;
import com.avitam.bankloanapplication.service.NodeService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/interface")
public class InterfaceController extends BaseController {
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NodeService nodeService;
    private static final String ADMIN_INTERFACE="/admin/interface";

    @PostMapping
    public NodeWsDto getAllNodes(@RequestBody NodeWsDto nodeWsDto){
        Pageable pageable=getPageable(nodeWsDto.getPage(),nodeWsDto.getSizePerPage(),nodeWsDto.getSortDirection(),nodeWsDto.getSortField());
        NodeDto nodeDto = CollectionUtils.isNotEmpty(nodeWsDto.getNodeDtos()) ? nodeWsDto.getNodeDtos().get(0) : new NodeDto();
        Node node = modelMapper.map(nodeDto, Node.class);
        Page<Node> page=isSearchActive(node) !=null ? nodeRepository.findAll(Example.of(node),pageable) : nodeRepository.findAll(pageable);
        nodeWsDto.setNodeDtos(modelMapper.map(page.getContent(), List.class));
        nodeWsDto.setTotalPages(page.getTotalPages());
        nodeWsDto.setTotalRecords(page.getTotalElements());
        nodeWsDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeWsDto;
    }
    @GetMapping("/get")
    public NodeWsDto getActiveInterface() {
        NodeWsDto nodeWsDto=new NodeWsDto();
        List<Node> nodes=nodeRepository.findByStatusOrderByIdentifier(true);
        nodeWsDto.setNodeDtos(modelMapper.map(nodes,List.class));
        nodeWsDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeWsDto;
    }



    @PostMapping("/getedit")
    public NodeWsDto editLoanApplication(@RequestBody NodeWsDto request) {
        NodeWsDto nodeWsDto = new NodeWsDto();
        List<Node> nodeList=new ArrayList<>();
        for(NodeDto nodeDto:request.getNodeDtos()) {
            nodeList.add(nodeRepository.findByRecordId(nodeDto.getRecordId()));
        }
        nodeWsDto.setNodeDtos(modelMapper.map(nodeList,List.class));
        nodeWsDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeWsDto;
    }

    @PostMapping("/edit")
    public NodeWsDto handleEdit(@RequestBody NodeWsDto request) {

        return nodeService.handleEdit(request);
    }


    @PostMapping("/delete")
    public NodeWsDto delete(@RequestBody NodeWsDto nodeWsDto) {
        for (NodeDto nodeDto: nodeWsDto.getNodeDtos()) {
            nodeRepository.deleteByRecordId(nodeDto.getRecordId());
        }
        nodeWsDto.setMessage("Data deleted successfully");
        nodeWsDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeWsDto;
    }
}
