package com.resotechsolutions.onboarding.mail;

import com.resotechsolutions.onboarding.entity.EmailContent;

public interface MailService {
    String welcomeEmail(String to,String username,String password,EmailContent emailContent);

    String passwordResetMail(String to, String otp, EmailContent emailContent);
}
