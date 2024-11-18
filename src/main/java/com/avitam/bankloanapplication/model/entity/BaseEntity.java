package com.avitam.bankloanapplication.model.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BaseEntity {

    private String recordId;
    @Id
    private ObjectId id;
    private String name;
 //   private String creator;
    private Boolean status;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
   private Date creationTime;
  //  @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
//    private Date lastModified;
//    private String modifiedBy;
//    private String pic;
    private String identifier;
}
