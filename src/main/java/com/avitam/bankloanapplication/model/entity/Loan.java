package com.avitam.bankloanapplication.model.entity;

import com.avitam.bankloanapplication.model.LoanScoreResult;
import com.avitam.bankloanapplication.model.LoanStatus;
import com.avitam.bankloanapplication.model.LoanType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document("loan")
public class Loan extends BaseEntity{

   private String loanTypeId;
   private Double loanLimit;
   private String loanScoreResultId;
   private String loanStatusId;
   private Date loanDate;
   private Integer creditMultiplier = 4;
   private String loanApplicationId;

    public Loan(LoanType loanType, double v, LoanScoreResult loanScoreResult, LoanStatus loanStatus, Date date) {
    }
}
