package com.resotechsolutions.onboarding.controller;

import com.resotechsolutions.onboarding.entity.dto.BankDto;
import com.resotechsolutions.onboarding.entity.dto.DocumentDto;
import com.resotechsolutions.onboarding.entity.dto.EducationDTO;
import com.resotechsolutions.onboarding.entity.dto.UserDTO;
import com.resotechsolutions.onboarding.response.BaseResponse;
import com.resotechsolutions.onboarding.response.ResponseHandler;
import com.resotechsolutions.onboarding.service.AppServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class FormController {
    private AppServiceImpl appService;
    private ResponseHandler responseHandler;

    private Log log = LogFactory.getLog(AppController.class);

    @Autowired
    public FormController(AppServiceImpl appService,ResponseHandler theResponseHandler){
        this.appService = appService;
        this.responseHandler = theResponseHandler;
    }
    @PostMapping("/update-details")
    public BaseResponse updateUserDetails(@RequestHeader String token, @RequestBody UserDTO userDTO){
        try {
            log.info("***********start of update user details api in Onboarding Controller " + new Date());
            userDTO.setToken(token);
            return appService.updateUserDetails(userDTO);
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }

    @PostMapping("/update-education")
    public BaseResponse updateEducationDetails(@RequestHeader("token")String token,@RequestBody EducationDTO educationDTO){
        try {
            log.info("***********start of update education details api in Onboarding form Controller " + new Date());
            return appService.updateEducationDetails(token,educationDTO);
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
    @PostMapping("/save-document")
    public BaseResponse updateDocumentDetails(@RequestHeader("token") String token, @RequestBody DocumentDto documentDto){
        try {
            log.info("***********start of document details update api in Onboarding form Controller " + new Date());
            return appService.updateDocumentDetails(token,documentDto);
        }catch (Exception e){
                log.info(e.toString());
                return responseHandler.setMessageResponse(-2);
        }
    }

    @PostMapping("/upload-document")
    public BaseResponse uploadUserDocuments(@RequestPart("file")MultipartFile file,
                                            @RequestHeader("token") String token,
                                            @RequestPart("name")String docType){
        try{
            log.info("***********start of upload document api in Onboarding form Controller " + new Date());
            return appService.updateUserDocuments(file,token,docType);
        }catch (Exception e){
                log.info(e.toString());
                return responseHandler.setMessageResponse(-2);
        }
    }
    @PostMapping("/upload-multiple-documents")
    public BaseResponse uploadUserDocuments(@RequestPart("files")MultipartFile[] files,
                                            @RequestHeader("token") String token){
        try{
            log.info("***********start of upload multiple document api in Onboarding form Controller " + new Date());
            BaseResponse response = new BaseResponse();
            List<String> list = new ArrayList<>();
            for (MultipartFile file : files) {
                response = appService.updateMultipleUserDocuments(file, token);
                if(response.getData() == null){
                    return responseHandler.setMessageResponse( response.getStatus().getStatusMessage(),-2,null);
                }
                list.add(response.getData().toString());
            }
            response.setData(list);
            return response;
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
    @PostMapping("/update-bank")
    public BaseResponse updateBankDetails(@RequestBody BankDto bankDto,@RequestHeader("token") String token){
        try{
            log.info("***********start of upload bank details api in Onboarding form Controller " + new Date());
            return appService.updateBankDetails(token,bankDto);
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
}
