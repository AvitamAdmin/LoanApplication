package com.avitam.bankloanapplication.web.controllers.admin.notification;

import com.avitam.bankloanapplication.model.dto.NotificationDto;
import com.avitam.bankloanapplication.model.entity.Notification;
import com.avitam.bankloanapplication.repository.NotificationRepository;
import com.avitam.bankloanapplication.service.NotificationService;
import com.avitam.bankloanapplication.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/admin/notification")
public class NotificationController extends BaseController {
    private static final String ADMIN_NOTIFICATION ="admin/notification" ;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public NotificationDto getAllRoles(@RequestBody NotificationDto notificationDto){
        Pageable pageable=getPageable(notificationDto.getPage(),notificationDto.getSizePerPage(),notificationDto.getSortDirection(),notificationDto.getSortField());
        Notification notification = modelMapper.map(notificationDto, Notification.class);
        Page<Notification> page=isSearchActive(notification) !=null ? notificationRepository.findAll(Example.of(notification),pageable) : notificationRepository.findAll(pageable);
        notificationDto.setNotifications(Collections.singletonList(page.getContent().toString()));
        notificationDto.setBaseUrl(ADMIN_NOTIFICATION);
        notificationDto.setTotalPages(page.getTotalPages());
        notificationDto.setTotalRecords(page.getTotalElements());
        return notificationDto;

    }
    @GetMapping("/get")
    public NotificationDto getNotificationById(@RequestBody NotificationDto request) {
        Notification notification=notificationRepository.findByRecordId(request.getRecordId());
        request=modelMapper.map(notification, NotificationDto.class);
        request.setBaseUrl(ADMIN_NOTIFICATION);
        return request;
    }

    @PostMapping("/create")
    public NotificationDto addCustomer(@RequestBody NotificationDto request) {

        return  notificationService.createNotification(request);
    }

    @PostMapping("/delete")
    public NotificationDto delete(@RequestBody NotificationDto notificationDto) {
        for (String id : notificationDto.getRecordId().split(",")) {
            notificationRepository.deleteByRecordId(id);
        }
        notificationDto.setMessage("Data deleted successfully");
        notificationDto.setBaseUrl(ADMIN_NOTIFICATION);
        return notificationDto;
    }
}
