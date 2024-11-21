package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.RoleDto;
import com.avitam.bankloanapplication.model.entity.Role;
import com.avitam.bankloanapplication.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.avitam.bankloanapplication.web.controllers.admin.role.RoleController.ADMIN_ROLE;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    public RoleDto createRole(RoleDto request) {

            RoleDto roleDto = new RoleDto();
            Role role = null;
            if(request.getRecordId()!=null){
                Role requestData = modelMapper.map(request,Role.class);
                role= roleRepository.findByRecordId(request.getRecordId());
                modelMapper.map(requestData, role);
                roleRepository.save(role);
            }
            else {
                role = modelMapper.map(request, Role.class);
                roleRepository.save(role);
            }
            if(request.getRecordId()==null){
                role.setRecordId(String.valueOf(role.getId().getTimestamp()));
            }
            roleRepository.save(role);
            roleDto=modelMapper.map(role, RoleDto.class);
            roleDto.setBaseUrl(ADMIN_ROLE);
            return roleDto;

        }

    }



