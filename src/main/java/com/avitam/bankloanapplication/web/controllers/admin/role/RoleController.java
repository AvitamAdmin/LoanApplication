package com.avitam.bankloanapplication.web.controllers.admin.role;


import com.avitam.bankloanapplication.model.dto.RoleDto;
import com.avitam.bankloanapplication.model.entity.Role;
import com.avitam.bankloanapplication.repository.RoleRepository;
import com.avitam.bankloanapplication.service.RoleService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/admin/Role")
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
    public RoleDto getAllRoles(@RequestBody RoleDto roleDto){
        Pageable pageable=getPageable(roleDto.getPage(),roleDto.getSizePerPage(),roleDto.getSortDirection(),roleDto.getSortField());
        Role role = modelMapper.map(roleDto, Role.class);
        Page<Role> page=isSearchActive(role) !=null ? roleRepository.findAll(Example.of(role),pageable) : roleRepository.findAll(pageable);
        roleDto.setRoles(Collections.singletonList(String.valueOf(page.getContent())));
        roleDto.setBaseUrl(ADMIN_ROLE);
        roleDto.setTotalPages(page.getTotalPages());
        roleDto.setTotalRecords(page.getTotalElements());
        return roleDto;

    }
    @PostMapping("/edit")
    @ResponseBody
    public RoleDto createRole(@RequestBody RoleDto request) {
        return roleService.createRole(request);

    }
    @PostMapping("/update")
    @ResponseBody
    public RoleDto updateRole(@RequestBody RoleDto request) {

        return roleService.createRole(request);
    }
    @PostMapping("/delete")
    @ResponseBody
    public RoleDto deleteLoan(@RequestBody RoleDto request){
        for (String id :request.getRecordId().split(",")){
            roleRepository.deleteByRecordId(id);
        }
        request.setMessage("Data deleted Successfully");
        request.setBaseUrl(ADMIN_ROLE);
        return request;
    }
    @GetMapping("/get")
    @ResponseBody
    public RoleDto getActiveRole(@RequestBody RoleDto request){
        Role role = roleRepository.findByRecordId(request.getRecordId());
        request=modelMapper.map(role, RoleDto.class);
        request.setBaseUrl(ADMIN_ROLE);
        return request;
    }

}