package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.NotificationDto;
import com.avitam.bankloanapplication.model.dto.NotificationWsDto;
import com.avitam.bankloanapplication.repository.NotificationRepository;
import com.avitam.bankloanapplication.model.entity.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private static final String ADMIN_NOTIFICATION = "/admin/notification";
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;


//    private Optional<Notification> findNotificationById(String id) {
//        return Optional.ofNullable(notificationRepository.findByRecordId(id));
//    }
//
//    public Notification getNotificationById(String id) {
//        return findNotificationById(id).get();
//    }

    public NotificationWsDto handelEdit(NotificationWsDto request) {
        NotificationWsDto notificationWsDto = new NotificationWsDto();
        Notification notification=new Notification();
        List<NotificationDto> notificationDtos = request.getNotificationDtoList();
        List<Notification> notifications= new ArrayList<>();
        for(NotificationDto notificationDto:notificationDtos) {
            if (notificationDto.getRecordId() != null) {
                notification = notificationRepository.findByRecordId(notificationDto.getRecordId());
                modelMapper.map(notificationDto, notification);
                notificationRepository.save(notification);
            } else {
                notification = modelMapper.map(notificationDto, Notification.class);
                notificationRepository.save(notification);
            }
            if (request.getRecordId() == null) {
                notification.setRecordId(String.valueOf(notification.getId().getTimestamp()));
            }
            notificationRepository.save(notification);
            notifications.add(notification);
            request.setBaseUrl(ADMIN_NOTIFICATION);

        }
        request.setNotificationDtoList(modelMapper.map(notifications,List.class));
        return request;
    }





}
