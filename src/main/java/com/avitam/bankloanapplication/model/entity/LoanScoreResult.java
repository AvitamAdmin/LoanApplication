package com.avitam.bankloanapplication.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("loanScore")
public class LoanScoreResult extends BaseEntity{
    private String description;
}
