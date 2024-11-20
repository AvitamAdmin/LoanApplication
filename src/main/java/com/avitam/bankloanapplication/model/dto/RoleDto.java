package com.avitam.bankloanapplication.model.dto;

import com.avitam.bankloanapplication.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RoleDto extends CommonDto{
    private Role role;
    private List<Role> roles;


}
