package com.avitam.bankloanapplication.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("role")
public class Role extends BaseEntity{

    private Set<Node> permissions;
    public String authority ;

}
