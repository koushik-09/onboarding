package com.resotechsolutions.onboarding.mail;

import com.resotechsolutions.onboarding.controller.AppController;
import com.resotechsolutions.onboarding.entity.EmailContent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImplementation implements MailService{

    private JavaMailSender mailSender;

    private Log log = LogFactory.getLog(MailServiceImplementation.class);

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
        message.setText("Welcome to ResoTech Solutions");
        try{
            mailSender.send(message);
            return "done";
        }
        catch (Exception e){
            return "failure";
        }
    }

    @Override
    public String passwordResetMail(String to,String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Password Reset");
        String text = "One-Time-Password to reset your password is :" + otp + " The OTP is valid for next 10minutes";
        message.setText(text);
        try{
            mailSender.send(message);
            return "done";
        }
        catch (Exception e){
            log.warn(e.toString());
            return "failure";
        }
    }

    @Override
    public String passwordResetMail(String to, String otp, EmailContent emailContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String content = emailContent.getBody();
            content = content.replace("AUTH_OTP", otp);
            helper.setText(content,true);
            helper.setSubject(emailContent.getSubject());
            helper.setFrom(from);
            helper.setTo(to);
            mailSender.send(message);
            return "OTP sent to your registered email";
        }catch (Exception e){
            log.warn(e.toString());
            return "Try again later";
        }
    }
}
