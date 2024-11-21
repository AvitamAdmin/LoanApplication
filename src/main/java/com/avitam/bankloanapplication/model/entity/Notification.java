package com.avitam.bankloanapplication.model.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Document("notification")
public class Notification extends BaseEntity{


    private String text;


    }
