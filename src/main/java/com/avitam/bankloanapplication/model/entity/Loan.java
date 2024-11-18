package com.avitam.bankloanapplication.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.avitam.bankloanapplication.model.LoanStatus;
import com.avitam.bankloanapplication.model.LoanScoreResult;
import com.avitam.bankloanapplication.model.LoanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("loan")
public class Loan extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ObjectId id;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private Double loanLimit;

    @Enumerated(EnumType.STRING)
    private LoanScoreResult loanScoreResult;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date loanDate;

    private final Integer creditMultiplier = 4;


    public Loan(LoanType loanType, Double loanLimit, LoanScoreResult loanScoreResult, LoanStatus loanStatus, Date loanDate) {
        this.loanType = loanType;
        this.loanLimit = loanLimit;
        this.loanScoreResult = loanScoreResult;
        this.loanStatus = loanStatus;
        this.loanDate = loanDate;
    }

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "loan")
    private LoanApplication loanApplication;

}
