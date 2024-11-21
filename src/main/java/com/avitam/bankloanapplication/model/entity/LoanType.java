package com.avitam.bankloanapplication.model.entity;

import com.avitam.bankloanapplication.model.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoanType extends BaseEntity {

    private String description;

}
