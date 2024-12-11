package com.avitam.bankloanapplication.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
public class CreditDto extends CommonDto{
    private Integer creditScore;
    private MultipartFile image;
}
