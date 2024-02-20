package com.resotechsolutions.onboarding.response;

import com.resotechsolutions.onboarding.entity.UserDetails;
import org.springframework.stereotype.Component;

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
        if(userDetails.getAddress() != null){
            data++;
            responseMap.put("address",userDetails.getAddress());
        }
        if(!userDetails.getDocuments().isEmpty()){
            data++;
            responseMap.put("Documents",userDetails.getDocuments());
        }
        responseMap.put("active",userDetails.isActive());
        responseMap.put("admin",userDetails.isAdmin());
        responseMap.put("passwordUpdated",userDetails.isPasswordUpdated());
        int percentage = (data*100)/6;
        responseMap.put("Percentage Complete",percentage);
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
            responseMap.put("address",true);
        }
        else{
            responseMap.put("address",false);
        }
        boolean pan = false;
        boolean aadhar = false;
        boolean markSheet = false;
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
                    markSheet = true;
                }
            }
        }
        responseMap.put("Pan",pan);
        responseMap.put("Aadhar",aadhar);
        responseMap.put("markSheet",markSheet);
        responseMap.put("active",userDetails.isActive());
        responseMap.put("admin",userDetails.isAdmin());
        responseMap.put("passwordUpdated",userDetails.isPasswordUpdated());
        System.out.println(data);
        int percentage = (data*100)/6;
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
