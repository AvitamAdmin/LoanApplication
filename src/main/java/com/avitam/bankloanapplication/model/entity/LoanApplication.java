package com.avitam.bankloanapplication.model.entity;

import com.avitam.bankloanapplication.model.dto.CustomerDto;
import com.avitam.bankloanapplication.model.dto.LoanTemplateDto;
import com.avitam.bankloanapplication.model.dto.LoanTypeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("loan_application")
public class LoanApplication extends BaseEntity {
    private String customerId;
    private String notificationId;
    private String loanId;
    private String loanStatus;
    private String images;
    private LocalDate sanctionDate;
    private LoanTypeDto loanTypeDto;
    private CustomerDto customerDto;
    private LoanTemplateDto loanTemplateDto;
}
