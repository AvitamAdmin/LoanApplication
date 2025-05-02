package com.avitam.bankloanapplication.mail.service;

import freemarker.template.Configuration;
import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    Configuration fmConfiguration;
    @Autowired
    Environment env;
    Logger logger = LoggerFactory.getLogger(MailService.class);
    @Autowired
    private JavaMailSenderImpl mailSender;

    private Properties getServerProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", env.getProperty("spring.transport.protocol"));
        properties.setProperty("mail.smtp.auth", env.getProperty("spring.smtp.auth"));
        properties.setProperty("mail.smtp.starttls.enable", env.getProperty("spring.smtp.starttls.enable"));
        properties.setProperty("mail.debug", env.getProperty("spring.mail.debug"));
        properties.setProperty("mail.smtp.ssl.enable", env.getProperty("spring.smtp.ssl.enable"));
        return properties;
    }

    public void sendCheilEmail(EMail mail) {
        MimeMessage mimeMessage = new MimeMessage(getSession());
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        try {
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(mail.getFrom());
            mimeMessageHelper.addAttachment(mail.getAttachment().getName(), mail.getAttachment());
            //mimeMessageHelper.setTo(mail.getTo());
            if (mail.getTo().contains(",")) {
                for (String mailTo : mail.getTo().split(",")) {
                    mimeMessageHelper.addTo(mailTo);
                }
            } else {
                mimeMessageHelper.addTo(mail.getTo());
            }
            //mail.setContent(geContentFromTemplate(mail.getModel()));
            mail.setContent("Please, find the details in the attachment");
            mimeMessageHelper.setText(mail.getContent(), true);
            logger.info("Sending mail to " + mail.getTo());
            Transport.send(mimeMessageHelper.getMimeMessage());
            logger.info("Sent mail to " + mail.getTo());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    private JavaMailSenderImpl getMailSender() {
        mailSender.setHost(env.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.valueOf(env.getProperty("spring.mail.port")));
        mailSender.setUsername(env.getProperty("spring.mail.username"));
        mailSender.setPassword(env.getProperty("spring.mail.password"));
        Properties props = mailSender.getJavaMailProperties();
        props.putAll(getServerProperties());
        return mailSender;
    }

    public void sendEmail(EMail mail) {
        MimeMessage mimeMessage = getMailSender().createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(mail.getFrom());
            //mimeMessageHelper.setTo(mail.getTo());
            for (String mailTo : mail.getTo().split(",")) {
                mimeMessageHelper.addTo(mailTo);
            }
            if (StringUtils.isEmpty(mail.getContent())) {
                mail.setContent(geContentFromTemplate(mail.getModel()));
            }
            mimeMessageHelper.setText(mail.getContent(), true);

            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }

    public String geContentFromTemplate(Map<String, Object> model) {
        StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-template.flth"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private Session getSession() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", env.getProperty("jakarta.mail.protocol"));
        props.setProperty("mail.host", env.getProperty("jakarta.mail.host"));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", env.getProperty("jakarta.mail.port"));
        props.put("mail.smtp.socketFactory.port", env.getProperty("jakarta.mail.port"));
        props.put("mail.smtp.socketFactory.class",
                "jakarta.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        return Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(env.getProperty("jakarta.mail.username"), env.getProperty("jakarta.mail.password"));
                    }
                });
    }
}
