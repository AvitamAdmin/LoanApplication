package com.avitam.bankloanapplication.mail;


import com.avitam.bankloanapplication.model.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService {
    String fromEmailId = "muthurethinam324@gmail.com";
    @Autowired
    private JavaMailSender javaMailSender;

    public EmailDto sendEmail(EmailDto request) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmailId);
        message.setTo(request.getTo());
        message.setText(request.getContent());
        message.setSubject(request.getSubject());

        javaMailSender.send(message);

        return request;
    }
}
