package com.avitam.bankloanapplication.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WebsiteSetting extends BaseEntity{

    private Binary logo;
    private Binary favicon;

}
