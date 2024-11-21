package com.avitam.bankloanapplication.model.dto;

import com.avitam.bankloanapplication.model.entity.BaseEntity;
import com.avitam.bankloanapplication.model.entity.Notification;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class NotificationDto extends CommonDto  {

    private String text;
    private List<String> notifications;

}
