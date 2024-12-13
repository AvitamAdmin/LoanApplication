package com.avitam.bankloanapplication.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class WebsiteSettingDto extends CommonDto{
    private MultipartFile logo;
    private MultipartFile favicon;
}
