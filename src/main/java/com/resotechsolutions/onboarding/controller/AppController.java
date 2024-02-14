package com.resotechsolutions.onboarding.controller;

import com.resotechsolutions.onboarding.entity.UserDTO;
import com.resotechsolutions.onboarding.response.BaseResponse;
import com.resotechsolutions.onboarding.response.ResponseHandler;
import com.resotechsolutions.onboarding.service.AppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    private AppServiceImpl appService;
    private ResponseHandler responseHandler;

    @Autowired
    public AppController(AppServiceImpl appService,ResponseHandler theResponseHandler){
        this.appService = appService;
        this.responseHandler = theResponseHandler;
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

    @GetMapping("/test")
    public BaseResponse test(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","koushik");
        map.put("email","koushik@gmail.com");
        map.put("token","qi746rtngqo8v7l2a3r7g6o8rg79");
        map.put("age",21);
        return responseHandler.setMessageResponse("done", HttpStatus.OK.value(),map);
    }
}
