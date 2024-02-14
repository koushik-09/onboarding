package com.resotechsolutions.onboarding.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImplementation implements MailService{

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public MailServiceImplementation(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public String welcomeEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Welcome");
        message.setText("Welcome to Resotech Solutions");
        try{
            mailSender.send(message);
            return "done";
        }
        catch (Exception e){
            return "failure";
        }
    }
}
