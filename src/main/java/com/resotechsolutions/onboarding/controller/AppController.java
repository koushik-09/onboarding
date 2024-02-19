package com.resotechsolutions.onboarding.controller;

import com.resotechsolutions.onboarding.entity.UserDTO;
import com.resotechsolutions.onboarding.response.BaseResponse;
import com.resotechsolutions.onboarding.response.ResponseHandler;
import com.resotechsolutions.onboarding.service.AppServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class AppController {

    private AppServiceImpl appService;
    private ResponseHandler responseHandler;

    private Log log = LogFactory.getLog(AppController.class);


    @Autowired
    public AppController(AppServiceImpl appService,ResponseHandler theResponseHandler){
        this.appService = appService;
        this.responseHandler = theResponseHandler;
    }

    @PostMapping("/save")
    public BaseResponse saveUser(@RequestBody UserDTO userDTO){
        try{
            log.info("***********start of register api in Onboarding Controller " + new Date());
            return appService.save(userDTO);
        }catch (Exception e){
            log.info(e.toString());
           return responseHandler.setMessageResponse(-2);
        }
    }

    @PostMapping("/login")
    public BaseResponse validateUser(@RequestHeader("username") String userName, @RequestHeader("password") String password){
        try {
            log.info("***********start of login api in Onboarding Controller " + new Date());
            return appService.validateUser(userName, password);
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }

    @PostMapping("/validate-token")
    public BaseResponse validateToken(@RequestHeader("token") String token){
        try {
            log.info("***********start of token validation api in Onboarding Controller " + new Date());
            return appService.validateToken(token);
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }

    @PostMapping("/update-password")
    public BaseResponse updatePassword(@RequestHeader("username") String username,
                                       @RequestHeader("password") String password,
                                       @RequestHeader("token") String token){
        try {
            log.info("***********start of password update api in Onboarding Controller " + new Date());
            return appService.updatePassword(username, password,token);
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
    @PostMapping("/validate-email")
    public BaseResponse validateEmail(@RequestBody UserDTO userDTO){
        try {
            log.info("***********start of validate email api in Onboarding Controller " + new Date());
            return appService.checkEmailExists(userDTO.getEmail());
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
    @PostMapping("/forget-password")
    public BaseResponse generateOtp(@RequestBody UserDTO userDTO){
        try {
            log.info("***********start of forget password api in Onboarding Controller " + new Date());
            return appService.generateOtp(userDTO.getEmail());
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
    @PostMapping("/validate-otp")
    public BaseResponse validateOtp(@RequestBody UserDTO userDTO){
        try {
            log.info("***********start of validate otp api in Onboarding Controller " + new Date());
            return appService.forgetPassword(userDTO.getEmail(), userDTO.getOtp(), userDTO.getPassword());
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
    @GetMapping("/get-headers")
    public BaseResponse getHeaders(){
        try {
            log.info("***********start of get headers api in Onboarding Controller " + new Date());
            return appService.getHeaders();
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
}
