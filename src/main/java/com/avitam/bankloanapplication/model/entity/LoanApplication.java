package com.avitam.bankloanapplication.model.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("loanApplication")
public class LoanApplication extends BaseEntity{

    private String customerId;
    private String notificationId;
    private String loanId;

    public LoanApplication(Customer dummyCustomer, Loan dummyLoan) {
    }
}
