package com.avitam.bankloanapplication.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class NotificationWsDto extends CommonWsDto{
    List<NotificationDto> notificationDtoList;



}