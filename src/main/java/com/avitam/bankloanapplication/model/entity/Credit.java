package com.avitam.bankloanapplication.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class Credit extends BaseEntity{
    private Integer creditScore;

    private Binary photo;

}
