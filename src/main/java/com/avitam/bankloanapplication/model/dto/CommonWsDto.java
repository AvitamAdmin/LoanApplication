package com.avitam.bankloanapplication.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommonWsDto extends PaginationDto{

    private String baseUrl;
    private String recordId;
    private String message;
    private Boolean success = true;

}
