package com.avitam.bankloanapplication.model.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@Document("notification")
public class Notification extends BaseEntity{


    private String text;


    }
