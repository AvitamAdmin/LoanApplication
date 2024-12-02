package com.avitam.bankloanapplication.web.controllers.admin.role;

import com.avitam.bankloanapplication.model.dto.RoleDto;
import com.avitam.bankloanapplication.model.dto.RoleWsDto;
import com.avitam.bankloanapplication.model.entity.Role;
import com.avitam.bankloanapplication.repository.RoleRepository;
import com.avitam.bankloanapplication.service.RoleService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModelMapper modelMapper;

    public static final String ADMIN_ROLE= "/admin/role";

    @PostMapping
    @ResponseBody
    public RoleWsDto getAllRoles(@RequestBody RoleWsDto roleWsDto){
        Pageable pageable=getPageable(roleWsDto.getPage(),roleWsDto.getSizePerPage(),roleWsDto.getSortDirection(),roleWsDto.getSortField());
        RoleDto roleDto = CollectionUtils.isNotEmpty(roleWsDto.getRoleDtoList()) ? roleWsDto.getRoleDtoList().get(0): new RoleDto();
        Role role = modelMapper.map(roleWsDto, Role.class);
        Page<Role> page=isSearchActive(role) !=null ? roleRepository.findAll(Example.of(role),pageable) : roleRepository.findAll(pageable);
        roleWsDto.setRoleDtoList(modelMapper.map(page.getContent(), List.class));
        roleWsDto.setBaseUrl(ADMIN_ROLE);
        roleWsDto.setTotalPages(page.getTotalPages());
        roleWsDto.setTotalRecords(page.getTotalElements());
        return roleWsDto;

    }
    @GetMapping("/get")
    @ResponseBody
    public RoleWsDto getActiveRole(@RequestBody RoleWsDto request){
        RoleWsDto roleWsDto = new RoleWsDto();
        List<Role> role = new ArrayList<>();
        for(RoleDto roleDto: request.getRoleDtoList()){
            role.add(roleRepository.findByRecordId(roleDto.getRecordId()));
        }
        roleWsDto.setRoleDtoList(modelMapper.map(role,List.class));
        roleWsDto.setBaseUrl(ADMIN_ROLE);
        return roleWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public RoleWsDto createRole(@RequestBody RoleWsDto request) {
        return roleService.createRole(request);

    }

    @PostMapping("/delete")
    @ResponseBody
    public RoleWsDto deleteLoan(@RequestBody RoleWsDto request){
        for(RoleDto roleDto:request.getRoleDtoList()){
            roleRepository.deleteByRecordId(roleDto.getRecordId());
        }
        request.setMessage("Data deleted Successfully");
        request.setBaseUrl(ADMIN_ROLE);
        return request;
    }


}