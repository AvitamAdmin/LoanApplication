package com.avitam.bankloanapplication.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("loan_application")
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="notification_id")
    private Notification notification;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="loan_id")
    private Loan loan;

    public LoanApplication(Customer customer, Loan loan) {
        this.customer = customer;
        this.loan = loan;
    }
}
