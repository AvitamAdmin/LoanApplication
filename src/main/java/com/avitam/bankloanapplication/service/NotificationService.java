package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.LoanScoreResult;
import com.avitam.bankloanapplication.model.dto.NotificationDto;
import com.avitam.bankloanapplication.model.entity.LoanApplication;
import com.avitam.bankloanapplication.repository.NotificationRepository;
import com.avitam.bankloanapplication.model.entity.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class NotificationService {
    private static final String ADMIN_NOTIFICATION = "/admin/notification";
    private final NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    private Optional<Notification> findNotificationById(String id) {
        return Optional.ofNullable(notificationRepository.findByRecordId(id));
    }

    public Notification getNotificationById(String id) {
        return findNotificationById(id).get();
    }

    public NotificationDto createNotification(NotificationDto request) {
        Notification notification=new Notification();
        if(request.getRecordId()!=null){
            notification=modelMapper.map(request,Notification.class);
            notification=notificationRepository.findByRecordId(request.getRecordId());
            modelMapper.map(notification,request);
            notificationRepository.save(notification);
        }else{
            notification=modelMapper.map(request, Notification.class);
            Instant now = Instant.now();
            notification.setCreationTime(Date.from(now));  // Save in UTC
            notification.setStatus(true);
            notificationRepository.save(notification);
        }
        notificationRepository.save(notification);
        if(request.getRecordId()==null){
            notification.setRecordId(String.valueOf(notification.getId().getTimestamp()));
            notificationRepository.save(notification);
        }
        request.setBaseUrl(ADMIN_NOTIFICATION);
        request.setMessage("Data saved Successfully");
        return request;
    }


        public String sendMessageForResult(LoanApplication loanApplication) {
        String resultMessage = "Your loan application is " + loanApplication.getStatus() ;
//       if(loanApplication.getLoan() == LoanScoreResult.APPROVED) {
//            resultMessage += " and your credit limit is " + creditApplication.getCreditLimit() + " TL.";
//        }

        return "Notification message is sent to " + " number with the message : " + resultMessage ;
    }

}
