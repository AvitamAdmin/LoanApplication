package com.avitam.bankloanapplication.model.entity;

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
   private String loanStatus;
   private String loanScoreResult;


}
