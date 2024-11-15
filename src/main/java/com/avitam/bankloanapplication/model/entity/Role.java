package com.avitam.bankloanapplication.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("role")
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {

    private int ids;
    private String roleId;
    private Set<Node> permissions;
}
