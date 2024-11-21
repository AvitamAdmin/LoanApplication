package com.avitam.bankloanapplication.model.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("loanScoreResult")
public class LoanScoreResult extends BaseEntity{
    private String description;

}
