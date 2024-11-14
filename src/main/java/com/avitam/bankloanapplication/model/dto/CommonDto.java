package com.avitam.bankloanapplication.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public class CommonDto {
        private String baseUrl;
        private String recordId;
        private String message;
        private int  totalPages;
        private long totalRecords;
        private boolean success = true;
    }

