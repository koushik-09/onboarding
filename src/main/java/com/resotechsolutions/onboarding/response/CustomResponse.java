package com.resotechsolutions.onboarding.response;

import com.resotechsolutions.onboarding.entity.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomResponse {


    public Map<String,Object> loginResponse(UserDetails userDetails){
        Map<String,Object> responseMap = new LinkedHashMap<>();
        int data = 1;
        responseMap.put("user_id",userDetails.getUser_id());
        responseMap.put("username",userDetails.getUserName());
        responseMap.put("firstName",userDetails.getFirstName());
        responseMap.put("lastName",userDetails.getLastName());
        responseMap.put("email",userDetails.getEmail());
        responseMap.put("token",userDetails.getUserToken());
        responseMap.put("active",userDetails.isActive());
        responseMap.put("admin",userDetails.isAdmin());
        responseMap.put("passwordUpdated",userDetails.isPasswordUpdated());
//        int percentage = (data*100)/6;
//        responseMap.put("Percentage Complete",percentage);
        return responseMap;
    }

    public Map<String,Object> landingResponse(UserDetails userDetails){
        Map<String,Object> responseMap = new LinkedHashMap<>();
        int data = 1;
        responseMap.put("user_id",userDetails.getUser_id());
        responseMap.put("username",userDetails.getUserName());
        responseMap.put("firstName",userDetails.getFirstName());
        responseMap.put("lastName",userDetails.getLastName());
        responseMap.put("email",userDetails.getEmail());
        responseMap.put("token",userDetails.getUserToken());
        if(userDetails.getAddress() != null){
            data++;
            responseMap.put("Personal Information",true);
        }
        else{
            responseMap.put("Personal Information",false);
        }
        boolean pan = false;
        boolean aadhar = false;
        boolean agreement = false;
        if(!userDetails.getDocuments().isEmpty()){
            for(int i=0;i<userDetails.getDocuments().size();i++){
                if(userDetails.getDocuments().get(i).getType() == 1){
                    data++;
                    pan = true;
                }else if(userDetails.getDocuments().get(i).getType() == 2){
                    data++;
                    aadhar = true;
                } else if (userDetails.getDocuments().get(i).getType() == 3) {
                    data++;
                    agreement = true;
                }
            }
        }
        responseMap.put("Pan Card",pan);
        responseMap.put("Aadhar Card",aadhar);
        responseMap.put("Agreement",agreement);
        if(!userDetails.getEducation().isEmpty()){
            data++;
            responseMap.put("Education Details",true);
        }
        else responseMap.put("Education Details",false);
        if(userDetails.getBank()!=null){
            data++;
            responseMap.put("Bank Details",true);
        }else responseMap.put("Bank Details",false);
        responseMap.put("active",userDetails.isActive());
        responseMap.put("admin",userDetails.isAdmin());
        responseMap.put("passwordUpdated",userDetails.isPasswordUpdated());
        int percentage = (data*100)/7;
        responseMap.put("Percentage Complete",percentage);
        return responseMap;
    }

    public Map<String,Object> userDetailsResponse(UserDetails userDetails){
        Map<String,Object> responseMap = new LinkedHashMap<>();
        int data = 1;
        responseMap.put("user_id",userDetails.getUser_id());
        responseMap.put("username",userDetails.getUserName());
        responseMap.put("firstName",userDetails.getFirstName());
        responseMap.put("lastName",userDetails.getLastName());
        responseMap.put("email",userDetails.getEmail());
        responseMap.put("token",userDetails.getUserToken());
        if(userDetails.getAddress() != null){
            data++;
            responseMap.put("address",userDetails.getAddress());
        }
        if(!userDetails.getEducation().isEmpty()){
            data++;
            for(int i=0;i<userDetails.getEducation().size();i++){
                if(userDetails.getEducation().get(i).getType() == 1){
                    userDetails.getEducation().get(i).setName("Graduation");
                }else if(userDetails.getEducation().get(i).getType() == 2){
                    userDetails.getEducation().get(i).setName("Secondary");
                }else if(userDetails.getEducation().get(i).getType() == 3){
                    userDetails.getEducation().get(i).setName("Primary");
                }
            }
            responseMap.put("Education Details",userDetails.getEducation());
        }
        if(!userDetails.getDocuments().isEmpty()){
            data+=userDetails.getDocuments().size();
            for(int i=0;i<userDetails.getDocuments().size();i++) {
                if (userDetails.getDocuments().get(i).getType() == 1) {
                    userDetails.getDocuments().get(i).setName("Pan Card");
                } else if (userDetails.getDocuments().get(i).getType() == 2) {
                    userDetails.getDocuments().get(i).setName("Aadhar Card");
                } else if (userDetails.getDocuments().get(i).getType() == 3) {
                    userDetails.getDocuments().get(i).setName("Agreement");
                }
            }
            responseMap.put("Documents",userDetails.getDocuments());
        }
        if(userDetails.getBank()!=null){
            data++;
            responseMap.put("Bank Details",userDetails.getBank());
        }
        responseMap.put("active",userDetails.isActive());
        responseMap.put("admin",userDetails.isAdmin());
        responseMap.put("passwordUpdated",userDetails.isPasswordUpdated());
        int percentage = (data*100)/7;
        responseMap.put("Percentage Complete",percentage);
        return responseMap;
    }
    public Map<String,Object> headerResponse(Map<String,String> primaryDetails,
                                             Map<String,String> graduation,
                                             Map<String,String> secondary,
                                             Map<String,String> primary,
                                             Map<String,String> pan,
                                             Map<String,String> aadhar,
                                             Map<String,String> agreement,
                                             Map<String,String> bank){
        Map<String,Object> responseMap = new LinkedHashMap<>();
        responseMap.put("Primary Details",primaryDetails);
        responseMap.put("Graduation",graduation);
        responseMap.put("Secondary Education",secondary);
        responseMap.put("Primary Education",primary);
        responseMap.put("Pan Details",pan);
        responseMap.put("Aadhar Details",aadhar);
        responseMap.put("Agreement",agreement);
        responseMap.put("Bank Details",bank);
        return responseMap;
    }
}
