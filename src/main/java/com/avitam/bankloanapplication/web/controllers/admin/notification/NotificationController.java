package com.avitam.bankloanapplication.web.controllers.admin.notification;

import com.avitam.bankloanapplication.model.entity.Notification;
import com.avitam.bankloanapplication.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/get/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long notificationId) {
        return new ResponseEntity(notificationService.getNotificationById(notificationId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity addCustomer(@RequestBody Notification notification) {
        notificationService.createNotification(notification);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
