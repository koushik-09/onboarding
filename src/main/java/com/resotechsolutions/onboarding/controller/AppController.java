package com.resotechsolutions.onboarding.controller;

import com.resotechsolutions.onboarding.entity.UserDTO;
import com.resotechsolutions.onboarding.mail.MailServiceImplementation;
import com.resotechsolutions.onboarding.response.BaseResponse;
import com.resotechsolutions.onboarding.response.ResponseHandler;
import com.resotechsolutions.onboarding.service.AppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    private AppServiceImpl appService;
    private ResponseHandler responseHandler;


    private MailServiceImplementation mailService;
    @Autowired
    public AppController(AppServiceImpl appService,ResponseHandler theResponseHandler,MailServiceImplementation mailService){
        this.appService = appService;
        this.responseHandler = theResponseHandler;
        this.mailService = mailService;
    }

    @PostMapping("/save")
    public BaseResponse saveUser(@RequestBody UserDTO userDTO){
        return appService.save(userDTO);
    }

    //request headers
    @PostMapping("/login")
    public BaseResponse validateUser(@RequestHeader("username") String userName, @RequestHeader("password") String password){
        return appService.validateUser(userName,password);
    }

    @PostMapping("/validate-token")
    public BaseResponse validateToken(@RequestHeader("token") String token){
        return appService.validateToken(token);
    }

    @PostMapping("/update-password")
    public BaseResponse updatePassword(@RequestHeader("username") String username,@RequestHeader("password") String password){
        return appService.updatePassword(username,password);
    }

    @GetMapping("/test")
    public BaseResponse test(){
        String str = mailService.welcomeEmail("medhireddykittureddy@gmail.com");
        return responseHandler.setMessageResponse("done", HttpStatus.OK.value(),null);
    }
}
