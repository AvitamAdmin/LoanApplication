package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.LoanDto;
import com.avitam.bankloanapplication.model.dto.RoleDto;
import com.avitam.bankloanapplication.model.entity.Loan;
import com.avitam.bankloanapplication.model.entity.Role;
import com.avitam.bankloanapplication.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.avitam.bankloanapplication.web.controllers.admin.RoleController.ADMIN_ROLE;

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
                Role requestData = request.getRole();
                role= roleRepository.findByRecordId(request.getRecordId());
                modelMapper.map(requestData, role);
                roleRepository.save(role);
            }
            else {
                role = request.getRole();
                roleRepository.save(role);
            }
            if(request.getRecordId()==null){
                role.setRecordId(String.valueOf(role.getId().getTimestamp()));
            }
            roleRepository.save(role);
            roleDto.setRole(role);
            roleDto.setBaseUrl(ADMIN_ROLE);
            return roleDto;

        }

    }



