package com.resotechsolutions.onboarding.controller;

import com.resotechsolutions.onboarding.entity.UserDTO;
import com.resotechsolutions.onboarding.response.BaseResponse;
import com.resotechsolutions.onboarding.response.ResponseHandler;
import com.resotechsolutions.onboarding.service.AppServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {

    private AppServiceImpl appService;
    private ResponseHandler responseHandler;


    private Log log = LogFactory.getLog(AppController.class);


    @Autowired
    public LoginController(AppServiceImpl appService,ResponseHandler theResponseHandler){
        this.appService = appService;
        this.responseHandler = theResponseHandler;
    }

    @PostMapping("/login")
    public BaseResponse validateUser(@RequestHeader("username") String userName, @RequestHeader("password") String password){
        try {
            log.info("***********start of login api in Onboarding Login Controller " + new Date());
            return appService.validateUser(userName, password);
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
    @PostMapping("/forget-password/generate-otp")
    public BaseResponse generateOtp(@RequestBody UserDTO userDTO){
        try {
            log.info("***********start of forget password api in Onboarding Login Controller " + new Date());
            return appService.generateOtp(userDTO.getEmail());
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
    @PostMapping("/forget-password/validate-otp")
    public BaseResponse validateOtp(@RequestBody UserDTO userDTO){
        try {
            log.info("***********start of validate otp api in Onboarding Login Controller " + new Date());
            return appService.validateOtp(userDTO.getEmail(), userDTO.getOtp());
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
    @PostMapping("/forget-password/change-password")
    public BaseResponse changePassword(@RequestBody UserDTO userDTO){
        try {
            log.info("***********start of change password api in Onboarding Login Controller " + new Date());
            return appService.changePassword(userDTO.getEmail(), userDTO.getPassword());
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
    @PostMapping("/forget-password/validate-otp-password")
    public BaseResponse validateOtpPassword(@RequestBody UserDTO userDTO){
        try {
            log.info("***********start of validate otp and password api in Onboarding Login Controller " + new Date());
            return appService.forgetPassword(userDTO.getEmail(), userDTO.getOtp(), userDTO.getPassword());
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
}
