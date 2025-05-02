package com.avitam.bankloanapplication.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmailDto extends CommonDto {

    private String to;
    private String from;
    private String subject;
    private String content;
    private Map<String, Object> model;
    private File attachment;
}
