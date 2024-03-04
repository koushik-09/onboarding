package com.resotechsolutions.onboarding.mail;

import com.resotechsolutions.onboarding.entity.EmailContent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
    public String welcomeEmail(String to, String username, String password, EmailContent emailContent) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String content = emailContent.getBody();
            content = content.replace("U_NAME",username);
            content = content.replace("PASS",password);
            helper.setText(content,true);
            helper.setSubject(emailContent.getSubject());
            helper.setFrom(from);
            helper.setTo(to);
            mailSender.send(message);
            return "Registration Success";
        }catch (Exception e){
            log.warn(e.toString());
            return "Try again later";
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
