package com.avitam.bankloanapplication.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchDto {
    private String label;
    private String attribute;
    private boolean dynamicAttr;
    private String dataType;
}
